import { ScrollView, StyleSheet, Text, View } from "react-native";
import { Parada, Posicao, PrevisaoChegada } from "../types/types";

type PrevisoesPorParada = {
  [key: number]: PrevisaoChegada[];
};

interface BusPredictionsProps {
  selectedParada: Parada | null;
  previsoesPorParada: PrevisoesPorParada;
  position: Posicao | null;
}
const BusPredictions: React.FC<BusPredictionsProps> = ({
  selectedParada,
  previsoesPorParada,
  position,
}) => (
  <>
    {selectedParada && (
      <View style={styles.previsoesContainer}>
        <Text style={styles.previsoesTitle}>
          Previsões para {selectedParada.np}
        </Text>
        {previsoesPorParada[selectedParada.cp] ? (
          previsoesPorParada[selectedParada.cp].map(
            (previsao: PrevisaoChegada, index: number) => (
              <ScrollView key={index} style={styles.previsao}>
                <Text>{`Consulta: ${previsao.hr}`}</Text>
                {previsao.p.l.map((linha: any, idx: number) => (
                  <View key={idx} style={styles.linha}>
                    <Text>{`Linha: ${linha.c}`}</Text>
                    <Text>{`Destino: ${linha.lt1}`}</Text>
                    <Text>{`Próximo ônibus: ${linha.vs.map((v: any) => v.t).join(", ")}`}</Text>
                  </View>
                ))}
              </ScrollView>
            ),
          )
        ) : (
          <Text>Carregando previsões...</Text>
        )}
      </View>
    )}
    {position && (
      <View style={styles.timeContainer}>
        <Text style={styles.timeText}>Última atualização: {position.hr}</Text>
      </View>
    )}
  </>
);

const styles = StyleSheet.create({
  timeContainer: {
    position: "absolute",
    bottom: 16,
    left: 16,
    backgroundColor: "rgba(255, 255, 255, 0.8)",
    padding: 8,
    borderRadius: 8,
  },
  timeText: {
    fontSize: 16,
  },
  previsoesContainer: {
    width: "100%",
    padding: 16,
    backgroundColor: "#f9f9f9",
    borderRadius: 8,
    marginTop: 16,
  },
  previsoesTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 8,
  },
  previsao: {
    marginBottom: 8,
  },
  linha: {
    paddingLeft: 8,
  },
});

export default BusPredictions;
