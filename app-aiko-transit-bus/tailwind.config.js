/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./App.{js,jsx,ts,tsx}",
    "./src/**/*.{js,jsx,ts,tsx}",  // Inclui todos os arquivos em 'src'
    "./<custom directory>/**/*.{js,jsx,ts,tsx}",  // Inclua outros diretórios conforme necessário
  ],
  theme: {
    extend: {},
    colors: {
      bluePrimary: "#003184",
      greenPrimary: "#00DF00",
      grayPrimary: "#404040"
    }
  },
  plugins: [],
}

