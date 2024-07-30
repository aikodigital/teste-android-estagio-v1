import React from "react";
import { View, StyleSheet } from 'react-native';
import MapView, { Marker } from 'react-native-maps';

export default function Mapa({ paradas }) {
    return (
        <View style={styles.container}>
            <MapView style={styles.mapinha}>
                {paradas.map((parada) => (
                    <Marker
                        key={parada.cp}
                        coordinate={{ latitude: parada.py, longitude: parada.px }}
                        title={parada.np}
                        description={parada.ed}
                    />
                ))}
            </MapView>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        width: "100%",
        height: "100%",
    },

    mapinha: {
        width: '100%',
        height: '100%',
    },
});
