import React, { useState } from "react";
import {
  ActivityIndicator,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { FontAwesome } from "@expo/vector-icons";
import { theme } from "../theme";
import axios from "axios";
import { router } from "expo-router";
import { MotiView, MotiText, MotiImage } from "moti";

export default function LogoImage() {
  const [loading, setLoading] = useState(false);
  const [connected, setConnected] = useState(false);

  const handleConnect = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        "https://aiko-olhovivo-proxy.aikodigital.io/Login/Autenticar?token=19d603094a9e979e97a455625e7db008ebd745ad85ed6c25c8892771ef9b4a1b",
      );

      if (response) {
        setConnected(true);
        router.navigate("/dashboard");
      } else {
        throw new Error("Erro ao conectar");
      }
    } catch (error) {
      console.error(error);
    }
    setLoading(false);
  };

  return (
    <View style={styles.container}>
      <MotiView
        from={{ opacity: 0, translateY: -50 }}
        animate={{ opacity: 1, translateY: 0 }}
        style={styles.header}
      >
        <MotiText
          from={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ type: "timing", duration: 1000 }}
          style={styles.title}
        >
          Bem-vindo(a) ao Sistema de Transporte
        </MotiText>
        <MotiImage
          from={{ opacity: 0, scale: 0.5 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ type: "timing", duration: 1000, delay: 200 }}
          source={{
            uri: "https://aiko.digital/wp-content/themes/aiko/assets/img/aiko-logo.png",
          }}
          style={styles.logo}
        />
      </MotiView>

      <View style={styles.body}>
        <MotiText
          from={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ type: "timing", duration: 500, delay: 400 }}
          style={styles.subtitle}
        >
          Para utilizar o sistema, é necessário conectar-se ao servidor.
        </MotiText>
        <TouchableOpacity
          style={styles.connectButton}
          onPress={handleConnect}
          disabled={loading || connected}
        >
          {loading ? (
            <ActivityIndicator
              size="small"
              color="white"
              style={styles.connectIcon}
            />
          ) : (
            <MotiView
              from={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              transition={{ type: "timing", duration: 500, delay: 600 }}
              style={{ flexDirection: "row", alignItems: "center" }}
            >
              <FontAwesome
                name="plug"
                size={24}
                color="white"
                style={styles.connectIcon}
              />
              <Text style={styles.connectButtonText}>Conectar-se</Text>
            </MotiView>
          )}
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    paddingHorizontal: 20,
    backgroundColor: "#f0f0f0",
  },
  header: {
    alignItems: "center",
    marginBottom: 40,
  },
  title: {
    fontSize: 24,
    textAlign: "center",
    marginBottom: 20,
    fontFamily: theme.fontFamily.bold,
  },
  logo: {
    width: 200,
    height: 200,
    resizeMode: "contain",
  },
  body: {
    alignItems: "center",
  },
  subtitle: {
    fontSize: 16,
    textAlign: "center",
    marginBottom: 20,
    fontFamily: theme.fontFamily.medium,
  },
  connectButton: {
    flexDirection: "row",
    backgroundColor: theme.colors.light_green,
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
    marginTop: 20,
  },
  connectButtonText: {
    color: "white",
    fontSize: 16,
    marginLeft: 10,
  },
  connectIcon: {
    marginRight: 10,
  },
});
