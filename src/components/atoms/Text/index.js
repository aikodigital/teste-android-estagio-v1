import React from 'react'
import { CustomText } from './styles'

export const Text = ({ children }) => {
    return (
        <CustomText>
            {children}
        </CustomText>
    )
}