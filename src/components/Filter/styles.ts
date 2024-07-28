import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  text: {
    fontSize: 20,
  },
  margin: {
    backgroundColor: "#000",
    height: 50,
    width: "100%",
    marginBottom: 5,
  },
  button: {
    backgroundColor: "#FFF",
    height: 50,
    width: "100%",
    marginBottom: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonImage: {
    width: 24,
    height: 24,
  },
  filterOptions: {
    marginTop: 10,
    padding: 10,
    backgroundColor: "#FFF",
    borderRadius: 5,
  },
  filterOption: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 10,
  },
  textInput: {
    marginLeft: 10,
    borderColor: "#ccc",
    borderWidth: 1,
    padding: 5,
    borderRadius: 5,
    flex: 1,
  },
});
