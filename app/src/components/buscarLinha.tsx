import { View, TextInput, Pressable, Text, FlatList} from 'react-native';
import { Feather } from '@expo/vector-icons';
import { ScrollView } from 'react-native-gesture-handler';
import {useState} from 'react'
import { getLine } from "../context/apiContexts"

export function BuscarLinha() {

    const [searchTerm, setSearchTerm] = useState('');
    const [lines, setLines ]  = useState([])

    const handleSearch = async () => {
        setLines([]);
        try {
            const data = await getLine(searchTerm);
            setLines(data);
            console.log(lines);
        } catch (error) {
            console.error('Error fetching lines:', error);
        }
    };

    const renderItem = ({ item   }) => (
        <View className='p-4 border-b border-gray-200'>
            <Text className='text-black font-bold'>{item.lt}</Text>
            <Text className='text-gray-600'>{item.tp}</Text>
            <Text className='text-gray-600'>{item.ts}</Text>
        </View>
    );


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
        
        <FlatList
                data={lines}
                keyExtractor={(item) => item.cl.toString()}
                renderItem={renderItem}
                ListEmptyComponent={<Text className='ml-32 text-gray-600'>Nada na listagem</Text>}
                className='mt-4'
            />
        
        </View> 
  );
}