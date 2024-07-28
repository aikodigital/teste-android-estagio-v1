const path = require('path');

module.exports = function (api) {
  api.cache(true);
  return {
    presets: ["babel-preset-expo"],
    plugins: [
      ["nativewind/babel"],
      [
        "module:react-native-dotenv",
        {
          moduleName: "@env",
          path: path.resolve(__dirname, '.env'),
          blacklist: null,
          whitelist: null,
          safe: false,
          allowUndefined: true,
        },
      ],
    ],
  };
};
