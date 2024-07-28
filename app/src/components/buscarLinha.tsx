import { View, TextInput, Pressable, Text} from 'react-native';
import { Feather } from '@expo/vector-icons';
import { ScrollView } from 'react-native-gesture-handler';
import {useState} from 'react'
import { getLine } from "../context/apiContexts"

export function BuscarLinha() {

    const [searchTerm, setSearchTerm] = useState('');
    const [lines, setLines ]  = useState([])

    const handleSearch = async () => {
        try {
            const data = await getLine(searchTerm);
            setLines(data);
            console.log(lines);
        } catch (error) {
            console.error('Error fetching lines:', error);
        }
    };


    return ( 
       <View>
        
        <View className='mt-10 w-full flex-row border border-slate-500 h-14 rounded-full items-center gap-2 px-4 bg-transparent'>
            <Feather name="search" size={24} color="#64748b" />

            <TextInput
                placeholder="Para onde vamos?"
                placeholderTextColor="text-black-50" 
                value={searchTerm}
                onChangeText={setSearchTerm}
                className='w-full h-full text-black flex-1 bg-transparent'/>

            <Pressable onPress={handleSearch} className='bg-neutral-500 p-3 rounded-full'>
                <Text className='text-white font-bold'>Procurar</Text>
            </Pressable>
        </View>
        


        <View className=''>
            <Text className='text-black font-bold'>Linhas</Text>
        </View>
        
        </View> 
  );
}