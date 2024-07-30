import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
  showModalButton: {
    marginTop: 20,
    position: "absolute",
    top: 50,
    left: 20,
    right: 20,
    zIndex: 1,
    backgroundColor: "#fff",
    borderRadius: 20,
    paddingHorizontal: 20,
    paddingVertical: 15,
    elevation: 4
  },
  containerModal: {
    width: "100%", // Ocupa toda a largura da tela
    height: "100%", // Ocupa 80% da altura da tela
    borderRadius: 10,
    backgroundColor: "white",
    paddingTop: 50,
  },
  closeButton: {
    display: "flex",
    marginBottom: 24,
  },
  modalContent: {
    padding: 20,
  },
  searchBar: {
    marginBottom: 16,
  },
  busLines: {
    display: "flex",
    flexDirection: "column",
    gap: 16,
    borderBottomLeftRadius: 10,
    borderBottomRightRadius: 10
  },
  map: {
    ...StyleSheet.absoluteFillObject,
    flex: 1,
    width: "100%",
    height: "100%",
  },
});
