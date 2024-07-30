import { useEffect, useRef, useState } from "react";
import { ScrollView, Text, TouchableOpacity, View } from "react-native";
import MapView, { Marker } from "react-native-maps";
import SearchBar from "../../components/SearchBar/index.js";
import { styles } from "./styles";

import axios from "axios";
import {
  getCurrentPositionAsync,
  LocationAccuracy,
  requestForegroundPermissionsAsync,
  watchPositionAsync,
} from "expo-location";
import { BusLine } from "../../components/BusLine";
import { ModalContainer } from "../../components/Modal/index";
import { BusStop } from "../../components/BusStop";
import { constants } from "../../constants/constants.js";

export function Home() {
  const [location, setLocation] = useState(null);
  const [busStops, setBusStops] = useState([]);
  const [vehicles, setVehicles] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [busLine, setBusLine] = useState([]);
  const [busStopForecast, setBusStopForecast] = useState([]);

  const [modalBusLine, setModalBusLine] = useState(false);
  const [modalBusStop, setModalBusStop] = useState(false);

  let instanceAxios = axios.create({
    baseURL: `${constants.application.baseUrl}${constants.application.version2}`,
    headers: {
      [constants.headers.authorization]:
        `${constants.headers.bearer} ${constants.TOKEN}`,
        [constants.headers.contentType]: constants.headers.applicationJson,
    },
  });

  async function requestLocationPermission() {
    const { granted } = await requestForegroundPermissionsAsync();

    if (granted) {
      const currentPosition = await getCurrentPositionAsync();
      setLocation(currentPosition);
    }
  }

  function Auth() {
    instanceAxios
      .post(
        `${constants.application.login}${constants.TOKEN}`
      )
      .then((response) => console.log(response.data));
  }

  function GetBusLine() {
    instanceAxios
      .get(`${constants.application.linha}${searchText}`)
      .then((busLine) => {
        setBusLine(busLine.data);
      });
  }

  function GetBusStopForecast(busStopCode) {
    instanceAxios
      .get(`${constants.application.previsao}${busStopCode}`)
      .then((busStop) => {
        setBusStopForecast(transformDataParada(busStop.data));
      });
  }

  function onHandleInputChange(text) {
    setSearchText(text);
  }

  function GetBusStops() {
    instanceAxios
      .get(constants.application.parada)
      .then((busStops) => {
        setBusStops(busStops.data);
      })
      .catch((error) => console.error(error));
  }

  function SearchBusLocation(busLineCode) {
    instanceAxios
      .get(`${constants.application.posicao}${busLineCode}`)
      .then((response) => {
        setVehicles(transformData(response.data));
      })
      .catch((error) => console.error(error));
  }


  function HandleModalBusLine() {
    setModalBusLine(!modalBusLine);
    setBusLine([]);
  }

  function HandleModalBusStop() {
    setModalBusStop(!modalBusStop);
  }

  function transformData(data) {
    return data.vs.map((item) => {
      return {
        hr: data.hr,
        p: item.p,
        a: item.a,
        ta: item.ta,
        py: item.py,
        px: item.px,
      };
    });
  }

  function transformDataParada(data) {
    return data.p.l.flatMap((item) =>
      item.vs.map((vehicle) => ({
        hr: data.hr,
        cp: data.p.cp,
        np: data.p.np,
        py: data.p.py,
        px: data.p.px,
        c: item.c,
        cl: item.cl,
        sl: item.sl,
        lt0: item.lt0,
        lt1: item.lt1,
        qv: item.qv,
        itemP: vehicle.p,
        itemT: vehicle.t,
        itemA: vehicle.a,
        itemTA: vehicle.TA,
        itemPY: vehicle.PY,
        itemPX: vehicle.PX,
        itemSV: vehicle.SV,
        itemIS: vehicle.IS,
      }))
    );
  }

  function onBusLineClick(linha) {
    SearchBusLocation(linha.cl);
    HandleModalBusLine();
  }

  useEffect(() => {
    requestLocationPermission();
  }, []);

  useEffect(() => {
    Auth();
    if (location?.coords) {
      GetBusStops();
    }
  }, [location]);


  // useEffect(() => {
  //   watchPositionAsync(
  //     {
  //       accuracy: LocationAccuracy.Highest,
  //       timeInterval: 1000,
  //       distanceInterval: 1,
  //     },
  //     (response) => {
  //       setLocation(response);
  //       mapRef.current?.animateCamera({
  //         pitch: 40,
  //         center: response.coords,
  //       });
  //     }
  //   );
  // });


  useEffect(() => {
    if (searchText) {
      GetBusLine();
    }
  }, [searchText]);

  return (
    <View style={styles.container}>
      {location && (
        <MapView
          style={styles.map}
          initialRegion={{
            latitude: -23.5979,
            longitude: -46.7202,
            latitudeDelta: 0.005,
            longitudeDelta: 0.005,
          }}

          
        >
          <Marker
            coordinate={{
              latitude: -23.5979,
              longitude: -46.7202,
            }}
          ></Marker>

          {vehicles.map((vehicle, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: vehicle.py,
                longitude: vehicle.px,
              }}
              image={require("../../../assets/icon-Bus.png")}
            ></Marker>
          ))}

          {busStops.map((busStop) => (
            <Marker
              onPress={() => {
                HandleModalBusStop();
                GetBusStopForecast(busStop.cp);
              }}
              key={busStop.cp}
              coordinate={{
                latitude: busStop.py,
                longitude: busStop.px,
              }}
              image={require("../../../assets/Icon-bus-stop2.png")}
            ></Marker>
          ))}
        </MapView>
      )}

      {!modalBusLine && !modalBusStop && (
        <TouchableOpacity
          onPress={HandleModalBusLine}
          style={styles.showModalButton}
        >
          <Text style={styles.showModalButtonText}>Pesquise por Linha</Text>
        </TouchableOpacity>
      )}

      {modalBusLine && (
        <View style={styles.containerModal}>
          <ModalContainer>
            <View style={styles.modalContent}>
              <View style={styles.closeButton}>
                <TouchableOpacity onPress={HandleModalBusLine}>
                  <Text style={styles.closeButtonText}>Voltar</Text>
                </TouchableOpacity>
              </View>
              <View style={styles.searchBar}>
                <SearchBar onHandleInput={onHandleInputChange}></SearchBar>
              </View>
              <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.busLines}>
                  {busLine.map((busAndLine, index) => (
                    <TouchableOpacity
                      key={index}
                      onPress={() => onBusLineClick(busAndLine)}
                    >
                      <BusLine busLine={busAndLine}></BusLine>
                    </TouchableOpacity>
                  ))}
                </View>
              </ScrollView>
            </View>
          </ModalContainer>
        </View>
      )}

      {modalBusStop && (
        <View style={styles.containerModal}>
          <ModalContainer onCloseModal={HandleModalBusStop}>
            <View style={styles.closeButton}>
              <TouchableOpacity onPress={HandleModalBusStop}>
                <Text style={styles.closeButtonText}>Voltar</Text>
              </TouchableOpacity>
            </View>
            <ScrollView showsVerticalScrollIndicator={false}>
              <View style={styles.busLines} >
                {busStopForecast.map((busStopForecast) => (
                  <BusStop 
                    key={busStopForecast.p}
                    previsao={busStopForecast}
                  ></BusStop>
                ))}
              </View>
            </ScrollView>
          </ModalContainer>
        </View>
      )}
    </View>
  );
}
