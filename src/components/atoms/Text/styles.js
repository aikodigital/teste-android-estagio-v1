import styled from "styled-components";

export const CustomText = styled.Text `
    font-size: 24px;
    margin-top: 20px;
    font-weight: bold;
    color: ${({ color }) => color || 'white' };
`;