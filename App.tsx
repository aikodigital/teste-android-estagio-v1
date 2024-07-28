import { styles } from './styles'
import MapView, { Marker } from 'react-native-maps'
import { useEffect, useState, useRef } from 'react'
import {
  SafeAreaView,
  ScrollView,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native'
import {
  requestForegroundPermissionsAsync,
  getCurrentPositionAsync,
  LocationObject,
  watchPositionAsync,
  LocationAccuracy,
} from 'expo-location'
import { BusFront, FlagTriangleRight, RefreshCcw } from 'lucide-react-native'

export default function App() {
  const [location, setLocation] = useState<LocationObject | null>(null)
  const [data, setData] = useState<any>({
    date: '',
    linhas: [],
  })

  const [searchLinhas, onChangeSearchLinhas] = useState('')
  const [listBus, setListBus] = useState([])
  const [listParadas, setListParadas] = useState([])
  const mapRef = useRef<MapView>(null)

  async function requestLocationPermissions() {
    const { granted } = await requestForegroundPermissionsAsync()

    if (granted) {
      const currentPosition = await getCurrentPositionAsync()
      setLocation(currentPosition)
    }
  }

  async function handleLinhas(linha) {
    setListBus(linha.vs)
    console.log(linha.cl)
    const data = await fetch(
      `https://aiko-olhovivo-proxy.aikodigital.io/parada/buscarParadasPorLinha?codigoLinha=${linha.cl}`,
      { method: 'GET' },
    )
      .then((r) => r.json())
      .catch((e) => console.log('erro', e))

    if (data) {
      console.log(data)
      setListParadas(data)
    }
  }

  async function getBusLocations() {
    const data = await fetch(
      'https://aiko-olhovivo-proxy.aikodigital.io/posicao',
      { method: 'GET' },
    )
      .then((r) => r.json())
      .catch((e) => console.log('erro', e))

    if (data) {
      setData({
        date: data.hr,
        linhas: data.l,
      })
    }
  }

  useEffect(() => {
    getBusLocations()
    requestLocationPermissions()
  }, [])

  useEffect(() => {
    watchPositionAsync(
      {
        accuracy: LocationAccuracy.Highest,
        timeInterval: 1000,
        distanceInterval: 1,
      },
      (Response) => {
        setLocation(Response)
      },
    )
  }, [])

  if (!data) {
    return (
      <SafeAreaView style={styles.container}>
        <Text style={styles.headerText}>carregando</Text>
      </SafeAreaView>
    )
  }

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>SPOn</Text>
      </View>
      {location && (
        <MapView
          showsCompass={true}
          ref={mapRef}
          style={styles.map}
          initialRegion={{
            latitude: location.coords.latitude,
            longitude: location.coords.longitude,
            latitudeDelta: 0.005,
            longitudeDelta: 0.005,
          }}
        >
          <Marker
            coordinate={{
              latitude: location.coords.latitude,
              longitude: location.coords.longitude,
            }}
          />

          {listBus.map((bus, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: bus.py,
                longitude: bus.px,
              }}
            >
              <BusFront />
            </Marker>
          ))}

          {listParadas.map((p, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: p.py,
                longitude: p.px,
              }}
            >
              <FlagTriangleRight />
            </Marker>
          ))}
        </MapView>
      )}
      <View style={styles.containerContent}>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
          <Text style={styles.containerContentHeader}>Linhas</Text>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <Text style={styles.containerContentRefresh}>
              ATUALIZADO AS {data.date}
            </Text>
            <TouchableOpacity
              onPress={() => getBusLocations()}
              style={{ marginLeft: 8 }}
              activeOpacity={0.5}
            >
              <RefreshCcw size={20} />
            </TouchableOpacity>
          </View>
        </View>
        <TextInput
          placeholder="Digite o numero da linha"
          style={styles.containerContentSearch}
          value={searchLinhas}
          onChangeText={onChangeSearchLinhas}
        />

        <View style={styles.containerContentSearchResult}>
          {data.linhas.length === 0 ? (
            <Text style={{ fontSize: 14, color: '#aaa', fontWeight: '700' }}>
              NENHUM RESULTADO ENCONTRADO
            </Text>
          ) : (
            <ScrollView style={{ flex: 1, width: '100%' }}>
              {data.linhas
                .filter((l) => l.c.includes(searchLinhas.toUpperCase()))
                .map((linha, index) => (
                  <TouchableOpacity
                    key={index}
                    style={styles.cardLinhas}
                    activeOpacity={0.7}
                    onPress={() => handleLinhas(linha)}
                  >
                    <View>
                      <Text style={{ fontSize: 16, fontWeight: '700' }}>
                        {linha.c}
                      </Text>
                      <Text style={{ fontSize: 12, color: '#666' }}>
                        cod. {linha.cl}
                      </Text>
                    </View>
                    <View>
                      <Text style={{ fontSize: 14 }}>{linha.lt0}</Text>
                      <Text style={{ fontSize: 14 }}>{linha.lt1}</Text>
                    </View>
                  </TouchableOpacity>
                ))}
            </ScrollView>
          )}
        </View>
      </View>
    </SafeAreaView>
  )
}
