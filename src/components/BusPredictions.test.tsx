import React from "react";
import { render } from "@testing-library/react-native";
import BusPredictions from "./BusPredictions";

describe("BusPredictions Component", () => {
  const mockSelectedParada = {
    cp: 12345,
    np: "Parada Teste",
  };

  const mockPrevisoesPorParada = {
    12345: [
      {
        hr: "09:00",
        p: {
          l: [
            {
              c: "1234",
              lt1: "Destino 1",
              vs: [{ t: "09:10" }, { t: "09:20" }],
            },
            {
              c: "5678",
              lt1: "Destino 2",
              vs: [{ t: "09:15" }, { t: "09:25" }],
            },
          ],
        },
      },
    ],
  };

  const mockPosition = {
    hr: "10:00",
  };

  it("should render predictions for selected parada", () => {
    const { getByText } = render(
      <BusPredictions
        selectedParada={mockSelectedParada}
        previsoesPorParada={mockPrevisoesPorParada}
        position={mockPosition}
      />,
    );

    expect(getByText(`Previsões para ${mockSelectedParada.np}`)).toBeTruthy();

    expect(getByText("Consulta: 09:00")).toBeTruthy();
    expect(getByText("Linha: 1234")).toBeTruthy();
    expect(getByText("Destino: Destino 1")).toBeTruthy();
    expect(getByText("Próximo ônibus: 09:10, 09:20")).toBeTruthy();
    expect(getByText("Linha: 5678")).toBeTruthy();
    expect(getByText("Destino: Destino 2")).toBeTruthy();
    expect(getByText("Próximo ônibus: 09:15, 09:25")).toBeTruthy();

    expect(getByText(`Última atualização: ${mockPosition.hr}`)).toBeTruthy();
  });

  it("should render loading message when no predictions available for selected parada", () => {
    const { getByText } = render(
      <BusPredictions
        selectedParada={mockSelectedParada}
        previsoesPorParada={{}}
        position={mockPosition}
      />,
    );

    expect(getByText("Carregando previsões...")).toBeTruthy();

    expect(getByText(`Última atualização: ${mockPosition.hr}`)).toBeTruthy();
  });

  it("should render only position when no selected parada", () => {
    const { getByText, queryByText } = render(
      <BusPredictions
        selectedParada={null}
        previsoesPorParada={mockPrevisoesPorParada}
        position={mockPosition}
      />,
    );

    expect(queryByText(`Previsões para ${mockSelectedParada.np}`)).toBeNull();

    expect(getByText(`Última atualização: ${mockPosition.hr}`)).toBeTruthy();
  });
});
