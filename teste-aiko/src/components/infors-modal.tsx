import { FontAwesome } from "@expo/vector-icons"
import React from "react"
import { StyleSheet, Text, View } from "react-native"

export const InforsModal = () => {
  return (
    <View style={styles.inforModal}>
      <View style={styles.textContainer}>
        <FontAwesome name="bus" color="red"  size={20} />
        <Text style={styles.textInfor}>Ônibus</Text>
      </View>
      <View style={styles.textContainer}>
        <FontAwesome name="bus" color="blue"  size={20} />
        <Text style={styles.textInfor}>Paradas</Text>
      </View>
    </View>
  )
}
const styles = StyleSheet.create({
  inforModal: {
    display: "flex",
    alignItems: "center",
    flexDirection: "row",
    justifyContent: "space-evenly",
    position: "absolute",
    width: 200,
    height: 60,
    top: 20,
    right: 10,
    backgroundColor: "rgba(0,0,0,0.4)",
    borderWidth: 2,
    borderRadius: 20,
    borderColor: "red",
  },
  textContainer: {
    display: "flex",
    alignItems: "center",
    flexDirection: "column",
  },
  textInfor: {
    color: "white",
    fontSize: 12,
  }
})
