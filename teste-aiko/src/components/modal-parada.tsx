// import React, { useState } from 'react';
// import { Button } from './Button';
// import { StyleSheet, Text, View } from 'react-native';
// import { Parada } from '../types/types';

// type Props = {
//   onClose: () => void;
//   onPress: () => void;
  
// };

// function ModalParadas() {
//   const [parada, setParada] = useState<Parada>();

//   return (
//     <View style={styles.centeredView}>
//       <View style={styles.modalView}>
//         {parada && (
//           <>
//             <Text style={styles.modalTextParada}>PARADA {parada.np}</Text>
//           </>
//         )}
//         <View style={{ display: 'flex', flexDirection: 'column', gap: 10 }}>
//           <Button
//             title='Ver Previsão de chegada'
//             onPress={() => setModalPrevisao(true)}
//           />
//           <Button
//             title='Fechar'
//             onPress={() => {
//               setModalParadaVisible(!modalParadaVisible);
//               setSelectedParada(null);
//             }}
//           />
//         </View>
//       </View>
//     </View>
//   );
// }

// const styles = StyleSheet.create({
//   centeredView: {
//     flex: 1,
//     justifyContent: 'center',
//     alignItems: 'center',
//     marginTop: 22,
//   },
//   modalView: {
//     margin: 20,
//     backgroundColor: 'white',
//     borderRadius: 20,
//     padding: 35,
//     shadowColor: '#000',
//     shadowOffset: {
//       width: 0,
//       height: 2,
//     },
//     shadowOpacity: 0.25,
//     shadowRadius: 4,
//     elevation: 5,
//   },
//   modalText: {
//     marginBottom: 10,
//     fontSize: 15,
//     textAlign: 'center',
//     fontWeight: '600',
//   },
//   modalTextParada: {
//     marginBottom: 50,
//     fontSize: 20,
//     textAlign: 'center',
//     fontWeight: '600',
//   },
// });

// export default ModalParadas;
