import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: 350,
    alignSelf: "center",
    borderRadius: 20,
    borderColor: "#ccc",
    borderWidth: 1,
    overflow: "hidden",
    backgroundColor: "#ccc",
  },
  itemVeiculo: {
    flex: 1,
    padding: 15,
    borderBottomWidth: 1,
    backgroundColor: "#fff",
    borderColor: "#ccc",
  },
  backItemContainer: {
    padding: 10,
  },
  itemContainer: {
    flex: 1,
    borderRadius: 20,
    padding: 15,
    backgroundColor: "#fff",
  },
  title: {
    fontWeight: "bold",
  },
  description: {
    color: "#555",
  },
});
