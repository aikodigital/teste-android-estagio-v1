import React, { useState, useEffect, useMemo } from "react";
import {
  View,
  Alert,
  TextInput,
  Switch,
  Text,
  FlatList,
  TouchableOpacity,
  Button,
  Image,
} from "react-native";
import MapView, { Marker } from "react-native-maps";
import { fetchVehiclePositions, fetchStops } from "../api/api";
import styles from "../utils/combinedScreenStyles";

const VehicleItem = React.memo(({ item, onSelect }) => (
  <TouchableOpacity style={styles.vehicleItem} onPress={() => onSelect(item)}>
    <Text style={styles.vehicleText}>Vehicle {item.p}</Text>
  </TouchableOpacity>
));

const VehicleList = ({ vehicles, onSelect }) => (
  <FlatList
    data={vehicles}
    keyExtractor={(item) => item.p.toString()}
    renderItem={({ item }) => <VehicleItem item={item} onSelect={onSelect} />}
    ListEmptyComponent={
      <Text style={styles.noVehicles}>No vehicles available</Text>
    }
    initialNumToRender={10}
  />
);

const stopIcon = require("../assets/icon.png");

const TrackingBus = ({ route }) => {
  const { token } = route.params;
  const [vehiclePositions, setVehiclePositions] = useState([]);
  const [stops, setStops] = useState([]);
  const [vehicleSearchTerm, setVehicleSearchTerm] = useState("");
  const [stopSearchTerm, setStopSearchTerm] = useState("");
  const [onlyActiveVehicles, setOnlyActiveVehicles] = useState(false);
  const [onlyActiveStops, setOnlyActiveStops] = useState(false);
  const [selectedVehicle, setSelectedVehicle] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const vehiclesPerPage = 10;

  useEffect(() => {
    loadVehiclePositions();
    loadStops();
  }, []);

  const loadVehiclePositions = async () => {
    try {
      const data = await fetchVehiclePositions(token);
      if (data && Array.isArray(data.l)) {
        setVehiclePositions(data.l);
      } else {
        Alert.alert("No Data", "No vehicle positions data available");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Failed to fetch vehicle positions");
    }
  };

  const loadStops = async () => {
    try {
      const data = await fetchStops(token);
      if (data && Array.isArray(data)) {
        setStops(data);
      } else {
        Alert.alert("No Data", "No stops data available");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Failed to fetch stops");
    }
  };

  const filteredVehiclePositions = useMemo(() => {
    const filteredById = vehiclePositions
      .flatMap((line) => (Array.isArray(line.vs) ? line.vs : []))
      .filter((vehicle) => {
        const vehicleIdStr = vehicle.p ? vehicle.p.toString() : "";
        return (
          vehicleSearchTerm === "" ||
          vehicleIdStr.toLowerCase().includes(vehicleSearchTerm.toLowerCase())
        );
      });

    const filtered = onlyActiveVehicles
      ? filteredById.filter((vehicle) => vehicle.a)
      : filteredById;

    const startIndex = (currentPage - 1) * vehiclesPerPage;
    const endIndex = startIndex + vehiclesPerPage;
    return filtered.slice(startIndex, endIndex);
  }, [vehiclePositions, vehicleSearchTerm, onlyActiveVehicles, currentPage]);

  const filteredStops = useMemo(() => {
    const upperCaseSearchTerm = stopSearchTerm.toUpperCase();
    return stops.filter((stop) => {
      const stopName = stop.np.toUpperCase();
      const matchesSearch = stopName.includes(upperCaseSearchTerm);
      const matchesActiveFilter = onlyActiveStops ? stop.active : true;
      return matchesSearch && matchesActiveFilter;
    });
  }, [stops, stopSearchTerm, onlyActiveStops]);

  const handleSelectVehicle = (vehicle) => {
    setSelectedVehicle(vehicle);
  };

  const handleLoadMore = () => {
    setCurrentPage((prevPage) => prevPage + 1);
  };

  const handleResetPagination = () => {
    setCurrentPage(1);
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.searchInput}
        placeholder="Search by Vehicle ID"
        value={vehicleSearchTerm}
        onChangeText={(text) => {
          setVehicleSearchTerm(text);
          handleResetPagination();
        }}
      />
      <TextInput
        style={styles.searchInput}
        placeholder="Search by Stop Name"
        value={stopSearchTerm}
        onChangeText={(text) => setStopSearchTerm(text)}
      />
      <View style={styles.filterContainer}>
        <View style={styles.switchContainer}>
          <Text>Only Active Vehicles</Text>
          <Switch
            value={onlyActiveVehicles}
            onValueChange={(value) => {
              setOnlyActiveVehicles(value);
              handleResetPagination();
            }}
          />
        </View>
        <View style={styles.switchContainer}>
          <Text>Only Active Stops</Text>
          <Switch
            value={onlyActiveStops}
            onValueChange={(value) => setOnlyActiveStops(value)}
          />
        </View>
      </View>
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: -23.55052,
          longitude: -46.633308,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        {filteredVehiclePositions.length > 0 ? (
          filteredVehiclePositions.map((vehicle) => (
            <Marker
              key={vehicle.p}
              coordinate={{ latitude: vehicle.py, longitude: vehicle.px }}
              title={`Vehicle ${vehicle.p}`}
            />
          ))
        ) : (
          <Marker
            coordinate={{ latitude: -23.55052, longitude: -46.633308 }}
            title="No Vehicles Available"
          />
        )}
        {filteredStops.map((stop) => (
          <Marker
            key={stop.cp}
            coordinate={{ latitude: stop.py, longitude: stop.px }}
            title={`Stop ${stop.np}`}
          >
            <Image
              source={stopIcon}
              style={{ width: 30, height: 30 }}
              resizeMode="contain"
            />
          </Marker>
        ))}
      </MapView>
      <VehicleList
        vehicles={filteredVehiclePositions}
        onSelect={handleSelectVehicle}
      />
      {selectedVehicle && (
        <View style={styles.selectedVehicleContainer}>
          <Text style={styles.selectedVehicleText}>
            Selected Vehicle: {selectedVehicle.p}
          </Text>
        </View>
      )}
      {filteredVehiclePositions.length > 0 && (
        <Button title="Load More" onPress={handleLoadMore} />
      )}
    </View>
  );
};

export default TrackingBus;
