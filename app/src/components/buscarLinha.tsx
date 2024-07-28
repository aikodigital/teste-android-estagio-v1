import { View, TextInput, Pressable, Text} from 'react-native';
import { Feather } from '@expo/vector-icons';
import { ScrollView } from 'react-native-gesture-handler';
import {useState} from 'react'
import ListLine from "../components/lineList"

export function BuscarLinha() {

    const [searchTerm, setSearchTerm] = useState('');

    return ( 
   <View className='mt-10 w-full flex-row border border-slate-500 h-14 rounded-full items-center gap-2 px-4 bg-transparent'>
        <Feather name="search" size={24} color="#64748b" />

        <TextInput
            placeholder="Para onde vamos?"
            placeholderTextColor="text-black-50" 
            className='w-full h-full text-black flex-1 bg-transparent'/>

        <Pressable className='bg-neutral-500 p-3 rounded-full'>
            <Text className='text-white font-bold'>Procurar</Text>
        </Pressable>
        
        
       
   </View> 
  );
}