import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#444",
    borderRadius: 20,
    overflow: "hidden",
    alignSelf: "center",
    margin: 20,
    maxHeight: 290,
  },
  map: {
    flex: 1,
    alignSelf: "center",
    width: 350,
    maxWidth: 400,
  },
  veiculoLine: {
    flex: 1,
    borderWidth: 1,
    borderColor: "#444",
  },
});
