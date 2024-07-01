import React from "react";
import { render, fireEvent } from "@testing-library/react-native";
import { Corredor } from "../types/types";
import CorredorList from "./CorredorList";

describe("CorredorList Component", () => {
  const mockCorredores: Corredor[] = [
    { cc: "1", nc: "Corredor 1" },
    { cc: "2", nc: "Corredor 2" },
    { cc: "3", nc: "Corredor 3" },
  ];

  const mockHandleSearchCorredor = jest.fn();
  const mockSetInputValue = jest.fn();

  it("should render list of corredores", () => {
    const { getByText } = render(
      <CorredorList
        corredores={mockCorredores}
        handleSearchCorredor={mockHandleSearchCorredor}
        setInputValue={mockSetInputValue}
      />,
    );

    mockCorredores.forEach((corredor) => {
      const corredorText = `${corredor.cc} - ${corredor.nc}`;
      expect(getByText(corredorText)).toBeTruthy();
    });
  });

  it("should call handleSearchCorredor and setInputValue when corredor item is pressed", () => {
    const { getByText } = render(
      <CorredorList
        corredores={mockCorredores}
        handleSearchCorredor={mockHandleSearchCorredor}
        setInputValue={mockSetInputValue}
      />,
    );

    fireEvent.press(getByText("2 - Corredor 2"));

    expect(mockHandleSearchCorredor).toHaveBeenCalledWith("2");
    expect(mockSetInputValue).toHaveBeenCalledWith("2");
  });
});
