import { Ionicons } from "@expo/vector-icons";
import { StyleSheet, Text, View, TouchableOpacity } from "react-native";
import { theme } from "../theme";
import { NavigationProp } from "@react-navigation/native";

interface HeaderProps {
  navigation: NavigationProp<any>;
}

const Header: React.FC<HeaderProps> = ({ navigation }) => (
  <View style={styles.header}>
    <TouchableOpacity
      onPress={() => navigation.goBack()}
      style={styles.backButton}
      testID="back-button"
    >
      <Ionicons name="arrow-back" size={24} color="white" />
    </TouchableOpacity>
    <View>
      <Text style={styles.headerText}>Monitoramento de Ônibus</Text>
      <Text style={styles.subHeaderText}>
        Veja a localização dos ônibus em tempo real
      </Text>
    </View>
  </View>
);

const styles = StyleSheet.create({
  header: {
    marginBottom: 16,
    alignItems: "center",
    flexDirection: "row",
    gap: 14,
    justifyContent: "space-between",
  },
  backButton: {
    backgroundColor: theme.colors.dark_blue,
    padding: 8,
    borderRadius: 8,
  },
  headerText: {
    fontSize: 20,
    fontWeight: "bold",
  },
  subHeaderText: {
    fontSize: 14,
    textAlign: "center",
  },
});

export default Header;
