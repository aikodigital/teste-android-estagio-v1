import { View, Modal } from "react-native";

export function ModalContainer({ children }) {
  return (
    <View>
      <Modal visible={true} transparent={true} animationType="slide">
        {children}
      </Modal>
    </View>
  );
}
