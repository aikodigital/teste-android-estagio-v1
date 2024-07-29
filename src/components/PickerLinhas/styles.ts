import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    borderWidth: 1,
    borderColor: "#ccc",
    backgroundColor: "#ccc",
  },
  button: {
    flexDirection: "row",
    alignItems: "center",
    padding: 10,
    backgroundColor: "#fff",
    borderRadius: 5,
    justifyContent: "space-between",
  },
  buttonText: {
    color: "#000",
    fontSize: 16,
  },
  errorText: {
    color: "red",
    marginBottom: 10,
    fontSize: 14,
  },
  list: {
    backgroundColor: "#fff",
    borderRadius: 20,
    maxHeight: 200,
    margin: 10,
  },
  itemButton: {
    padding: 10,
    borderRadius: 20,
    borderBottomWidth: 1,
    borderBottomColor: "#ddd",
  },
  itemText: {
    fontSize: 16,
  },
  disabledButton: {},
  disabledText: {
    color: "gray",
  },
});
