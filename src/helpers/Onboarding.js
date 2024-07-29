import React, { useState } from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import GestureRecognizer from 'react-native-swipe-gestures';
import { useNavigation } from '@react-navigation/native';

export default function OnboardingCarousel() {
    const navigation = useNavigation();
    const [pageIndex, setPageIndex] = useState(0);

    const pages = [
        {
            subtitle: <Text> O <Text style={styles.bold}>LocaBuSP </Text> Aplicativo de busca de informações sobre o transporte publico de SaoPaulo</Text>,
            image: require('../../assets/logos/Locabusp_logo.png')
        }
    ];

    const handleNext = () => {
        if (pageIndex < pages.length - 1) {
            setPageIndex(pageIndex + 1);
        } else {
            navigation.navigate('RealTime');
        }
    };

    const handlePrevious = () => {
        if (pageIndex > 0) {
            setPageIndex(pageIndex - 1);
        }
    };

    return (
        <GestureRecognizer onSwipeLeft={handleNext} onSwipeRight={handlePrevious} style={styles.container}>
            <View style={styles.contentContainer}>
                <Image source={pages[pageIndex].image} style={styles.image} />
                <View style={styles.textContainer}>
                    <Text style={styles.title}>{pages[pageIndex].title}</Text>
                    <Text style={styles.subtitle}>{pages[pageIndex].subtitle}</Text>
                </View>
                <View style={styles.dotsContainer}>
                    {pages.map((_, index) => (
                        <View
                            key={index}
                            style={[
                                styles.dot,
                                index === pageIndex && styles.activeDot,
                            ]}
                        />
                    ))}
                </View>
                <TouchableOpacity style={styles.nextButton} onPress={handleNext}>
                    <Text style={styles.nextButtonText}>
                        {pageIndex === pages.length - 1 ? 'Continuar para o app' : 'Seguinte'}
                    </Text>
                </TouchableOpacity>
            </View>
        </GestureRecognizer>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f0f4f8',
        justifyContent: 'center',
        alignItems: 'center',
    },
    contentContainer: {
        width: '100%',
        paddingHorizontal: 20,
        justifyContent: 'center',
        alignItems: 'center',
    },
    image: {
        width: 200,
        height: 200,
        resizeMode: 'contain',
        marginBottom: 20,
    },
    textContainer: {
        alignItems: 'center',
        marginBottom: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#003184',
        textAlign: 'center',
        marginBottom: 10,
    },
    subtitle: {
        fontSize: 16,
        color: '#555',
        textAlign: 'center',
        paddingHorizontal: 10,
    },
    dotsContainer: {
        flexDirection: 'row',
        marginVertical: 20,
    },
    dot: {
        width: 10,
        height: 10,
        borderRadius: 5,
        backgroundColor: '#ddd',
        marginHorizontal: 5,
    },
    activeDot: {
        backgroundColor: '#003184',
    },
    nextButton: {
        backgroundColor: '#003184',
        borderRadius: 25,
        paddingVertical: 15,
        paddingHorizontal: 30,
    },
    nextButtonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    bold: {
        fontWeight: 'bold',
    },
});
