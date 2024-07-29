import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
  searchContainer: {
    alignSelf: "center",
    flexDirection: "row",
    alignItems: "center",
    marginVertical: 8,
  },
  searchIcon: {
    position: "absolute",
    right: 20,
    top: 12,
  },
  searchInput: {
    paddingLeft: 20,
    height: 50,
    alignSelf: "center",
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 20,
    width: 350,
    paddingHorizontal: 10,
    marginBottom: 20,
  },
  mapContainer: {
    flex: 1,
  },
  paradasContainer: {},
  textError: {},
  loadingContainer: {
    alignItems: "center",
    justifyContent: "center",
    padding: 20,
  },
  errorText: {
    color: "red",
    marginBottom: 10,
    fontSize: 14,
  },
  pickerContainer: {
    padding: 10,
  },
  noContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  noText: {
    fontSize: 18,
    color: "#666",
  },
});
