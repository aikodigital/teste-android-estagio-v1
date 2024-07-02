import React from "react";
import { render, fireEvent } from "@testing-library/react-native";
import SearchInput from "./SearchInput";
import { Corredor } from "../types/types";

describe("SearchInput Component", () => {
  const mockProps = {
    selectedOption: "linha" as const,
    inputValue: "",
    setInputValue: jest.fn(),
    handleSearchLinha: jest.fn(),
    handleSearchCorredor: jest.fn(),
    corredores: [
      { cc: "1", nc: "Corredor A" },
      { cc: "2", nc: "Corredor B" },
    ] as Corredor[],
  };

  it("should render input for linha option", () => {
    const { getByPlaceholderText, getByText } = render(
      <SearchInput {...mockProps} />,
    );

    const inputElement = getByPlaceholderText("Digite o linha");
    expect(inputElement).toBeTruthy();

    const searchButton = getByText("Buscar");
    expect(searchButton).toBeTruthy();
  });

  it("should call handleSearchLinha when search button is pressed", () => {
    const { getByText, getByPlaceholderText } = render(
      <SearchInput {...mockProps} />,
    );

    const inputElement = getByPlaceholderText("Digite o linha");
    fireEvent.changeText(inputElement, "Ramos");

    const searchButton = getByText("Buscar");
    fireEvent.press(searchButton);
  });

  it("should render corredor list for corredor option", () => {
    const { getByText } = render(
      <SearchInput {...mockProps} selectedOption="corredor" />,
    );

    const corredorItem1 = getByText("1 - Corredor A");
    expect(corredorItem1).toBeTruthy();

    const corredorItem2 = getByText("2 - Corredor B");
    expect(corredorItem2).toBeTruthy();
  });

  it("should call handleSearchCorredor when corredor item is pressed", () => {
    const { getByText } = render(
      <SearchInput {...mockProps} selectedOption="corredor" />,
    );

    const corredorItem1 = getByText("1 - Corredor A");
    fireEvent.press(corredorItem1);

    expect(mockProps.setInputValue).toHaveBeenCalledWith("1");
    expect(mockProps.handleSearchCorredor).toHaveBeenCalledWith("Corredor A");
  });
});
