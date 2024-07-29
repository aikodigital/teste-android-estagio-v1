//import React, { useState, useEffect } from 'react';
//import { View, Text, ActivityIndicator, StyleSheet } from 'react-native';
//import ConfigAPI from '../config/services/ConfigAPI'; 
//import RNFS from 'react-native-fs'; // Para manipulação de arquivos
//import { WebView } from 'react-native-webview'; // Para renderizar KML

//const Speed = () => {
//  const [mapUrl, setMapUrl] = useState(null);
//  const [error, setError] = useState('');
//  const [loading, setLoading] = useState(true);

  // Função para baixar e processar o arquivo KMZ
//  const fetchAndProcessKMZ = async () => {
//    try {
      // Faz a chamada à API para obter o arquivo KMZ
 //     const response = await ConfigAPI.get('/KMZ/BC', { responseType: 'blob' });
      
      // Salva o arquivo KMZ localmente
 //     const filePath = `${RNFS.DocumentDirectoryPath}/map.kmz`;
 //     await RNFS.writeFile(filePath, response.data, 'base64');
      
      // Extrai o arquivo KMZ para obter o arquivo KML
 //     const unzipDir = `${RNFS.DocumentDirectoryPath}/map`;
 //     await RNFS.mkdir(unzipDir);
 //     await RNFS.unzip(filePath, unzipDir);
      
      // Localiza o arquivo KML dentro do KMZ descompactado
//      const kmlFiles = await RNFS.readDir(unzipDir);
//      const kmlFile = kmlFiles.find(file => file.name.endsWith('.kml'));

 //     if (kmlFile) {
 //       setMapUrl(`file://${kmlFile.path}`);
 //     } else {
 //       setError('Arquivo KML não encontrado no KMZ.');
 //     }
  //  } catch (err) {
 //     setError('Erro ao carregar o mapa.');
 //   } finally {
 //     setLoading(false); 
 //   }
 // };

 // useEffect(() => {
 //   fetchAndProcessKMZ(); 
 // }, []);

 // return (
 //   <View style={styles.container}>
 //     {loading ? (
 //       <ActivityIndicator size="large" color="#0000ff" />
 //     ) : error ? (
  //      <Text style={styles.error}>{error}</Text>
 //     ) : mapUrl ? (
 //       <WebView
 //         source={{ uri: mapUrl }}
 //         style={styles.map}
 //       />
 //     ) : (
 //       <Text style={styles.error}>Nenhum mapa disponível.</Text>
 //     )}
 //   </View>
 //// );
//};

//const styles = StyleSheet.create({
  //container: {
  //  flex: 1,
 //   padding: 20,
 //   backgroundColor: '#fff',
 // },
 // map: {
 //   flex: 1,
 // },
 // error: {
 //   color: 'red',
 //   textAlign: 'center',
 //   marginTop: 20,
 // },
//});

//export default Speed;
