import React from "react";
import { render, fireEvent } from "@testing-library/react-native";
import { NavigationContainer } from "@react-navigation/native";
import Header from "./Header";

describe("Header Component", () => {
  const mockedNavigate = jest.fn();

  const navigation = {
    goBack: mockedNavigate,
  };

  it("should render correctly", () => {
    const { getByText } = render(
      <NavigationContainer>
        <Header navigation={navigation as any} />
      </NavigationContainer>,
    );

    expect(getByText("Monitoramento de Ônibus")).toBeTruthy();
    expect(
      getByText("Veja a localização dos ônibus em tempo real"),
    ).toBeTruthy();
  });

  it("should call navigation.goBack on back button press", () => {
    const { getByTestId } = render(
      <NavigationContainer>
        <Header navigation={navigation as any} />
      </NavigationContainer>,
    );

    fireEvent.press(getByTestId("back-button"));

    expect(mockedNavigate).toHaveBeenCalled();
  });
});
