import React from "react";
import {createNativeStackNavigator} from "@react-navigation/native-stack"

import Linhas from "./Linhas";
import RotaSP from "./RotaSP";
import Paradas from "./Paradas";
import Posicao from "./Posicao";
import Previsao from "./Previsao"

const AppStack = createNativeStackNavigator()

const Routes = () => (
    <AppStack.Navigator>
        <AppStack.Screen
            name="RotaSP"
            component={RotaSP}
        />
        <AppStack.Screen
            name="Linhas"
            component={Linhas}
        />
        <AppStack.Screen
            name="Paradas"
            component={Paradas}
        />
        <AppStack.Screen
            name="Posicao"
            component={Posicao}
        />
            <AppStack.Screen
            name="Previsao"
            component={Previsao}
        />
    </AppStack.Navigator>
) 

export default Routes