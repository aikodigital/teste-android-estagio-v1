import React from "react";
import { render } from "@testing-library/react-native";
import MapViewContainer, { Position } from "./MapViewContainer";

import { Region } from "react-native-maps";
import { Parada } from "../types/types";

describe("MapViewContainer Component", () => {
  const mockParadas: Parada[] = [
    { cp: 12345, np: "Parada 1", py: -23.5, px: -46.6 },
    { cp: 67890, np: "Parada 2", py: -23.6, px: -46.7 },
  ];

  const mockPosition: Position = {
    hr: "09:00",
    vs: [
      { p: "001", py: -23.55, px: -46.65 },
      { p: "002", py: -23.56, px: -46.66 },
    ],
  };

  const mockRegion: Region = {
    latitude: -23.55,
    longitude: -46.65,
    latitudeDelta: 0.1,
    longitudeDelta: 0.1,
  };

  const mockHandleSelectParada = jest.fn();

  it("should render map when position is provided", () => {
    const { getByTestId } = render(
      <MapViewContainer
        position={mockPosition}
        paradas={mockParadas}
        handleSelectParada={mockHandleSelectParada}
        mapRegion={mockRegion}
        selectedParada={null}
        previsoesPorParada={{}}
      />,
    );
    const mapView = getByTestId("map-view");
    expect(mapView).toBeTruthy();
  });

  it("should render loading indicator when position is null", () => {
    const { getByText } = render(
      <MapViewContainer
        position={null}
        paradas={mockParadas}
        handleSelectParada={mockHandleSelectParada}
        mapRegion={mockRegion}
        selectedParada={null}
        previsoesPorParada={{}}
      />,
    );

    expect(getByText("Carregando...")).toBeTruthy();
  });
});
