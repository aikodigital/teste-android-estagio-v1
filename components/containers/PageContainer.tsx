import React from 'react';
import { StyleSheet, View, ViewProps } from 'react-native';

const PageContainer = (props: ViewProps) => {
  return (
    <View style={styles.pageContainer} {...props}>
      {props.children}
    </View>
  );
};

export default PageContainer;

const styles = StyleSheet.create({
  pageContainer: {
    paddingTop: 28,
    paddingBottom: 120,
    paddingHorizontal: 14,
    backgroundColor: '#f0f4f8',
  },
});
