module.exports = {
  root: true,
  extends: '@react-native',
  parser: '@typescript-eslint/parser',
  plugins: ['@typescript-eslint'],
  rules: {
    'no-console': 'off',
    'prettier/prettier': [
      'error',
      {
        endOfLine: 'auto',
      },
    ],
  },
  overrides: [
    {
      files: ['*.ts', '*.tsx'],
      rules: {
        'react-hooks/exhaustive-deps': 'off',
        'no-sequences': 'off',
        '@typescript-eslint/no-shadow': 'off',
        'react-native/no-inline-styles': 'off',
        'no-shadow': 'off',
        'no-undef': 'off',
      },
    },
  ],
};
