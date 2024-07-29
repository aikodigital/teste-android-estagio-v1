import React, { useState, useEffect } from 'react';
import { View, StyleSheet, Dimensions } from 'react-native';
import MapView, { Marker } from 'react-native-maps';

interface Vehicle {
    p: number;
    a: boolean;
    ta: string;
    py: number;
    px: number;
    sv: null;
    is: null;
}

const MapScreen = () => {
    const [vehicles, setVehicles] = useState<Vehicle[]>([]);

  

    return (
        <View>
            {/* <MapView
                style={styles.map}
                initialRegion={{
                    latitude: -22.9068,
                    longitude: -43.1729,
                    latitudeDelta: 0.0922,
                    longitudeDelta: 0.0421,
                }} */}
        </View>
    )
}


const styles = StyleSheet.create({

    map:{

    },

    container: {
        flex: 1,
        alignItems: 'center',
    },
});

export default MapScreen;
