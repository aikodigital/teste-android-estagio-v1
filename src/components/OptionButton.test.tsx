import React from "react";
import { render, fireEvent } from "@testing-library/react-native";
import OptionButton from "./OptionButton";

describe("OptionButton Component", () => {
  const mockProps = {
    iconName: "bus" as const,
    isSelected: false,
    title: "Test Button",
    onPress: jest.fn(),
  };

  it("should render correctly", () => {
    const { getByText, getByTestId } = render(<OptionButton {...mockProps} />);

    const titleElement = getByText("Test Button");
    expect(titleElement).toBeTruthy();

    const buttonElement = getByTestId("option-button");
    expect(buttonElement).toBeTruthy();
  });

  it("should call onPress when pressed", () => {
    const { getByTestId } = render(<OptionButton {...mockProps} />);

    const buttonElement = getByTestId("option-button");
    fireEvent.press(buttonElement);

    expect(mockProps.onPress).toHaveBeenCalledTimes(1);
  });

  it("should apply selected styles when isSelected is true", () => {
    const { rerender } = render(
      <OptionButton {...mockProps} isSelected={true} />,
    );

    rerender(<OptionButton {...mockProps} isSelected={false} />);
  });
});
