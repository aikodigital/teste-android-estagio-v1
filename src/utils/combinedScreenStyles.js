// src/styles/combinedScreenStyles.js
import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
  },
  searchInput: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    paddingHorizontal: 8,
    marginVertical: 5,
    width: "100%",
  },
  filterContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginVertical: 10,
    width: "100%",
  },
  switchContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  map: {
    width: "100%",
    height: 400,
    marginVertical: 10,
  },
  vehicleItem: {
    padding: 10,
    borderBottomColor: "gray",
    borderBottomWidth: 1,
  },
  vehicleText: {
    fontSize: 16,
  },
  vehicleDetails: {
    marginTop: 5,
  },
  noVehicles: {
    padding: 20,
    textAlign: "center",
    color: "gray",
  },
  selectedVehicleContainer: {
    marginTop: 20,
    padding: 10,
    backgroundColor: "#f0f0f0",
    width: "100%",
  },
  selectedVehicleText: {
    fontSize: 18,
    fontWeight: "bold",
  },
});

export default styles;
