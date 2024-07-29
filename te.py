import requests
import json

# URL do JSON
url = "https://pgeo3.rio.rj.gov.br/arcgis/rest/services/Hosted/Esta%C3%A7%C3%B5es_BRT/FeatureServer/0/query?where=1%3D1&outFields=nome,tipo&outSR=4326&f=json"

# Baixar o JSON
response = requests.get(url)
data = response.json()

# Extrair coordenadas, nome e tipo
cleaned_data = []
for feature in data['features']:
    coordenada = feature['geometry']['points'][0]  # Coordenadas
    nome = feature['attributes']['nome']           # Nome
    tipo = feature['attributes']['tipo']           # Tipo
    
    cleaned_data.append({
        'coordenada': coordenada,
        'nome': nome,
        'tipo': tipo
    })

# Salvar em um novo arquivo JSON com a codificação correta
with open('brt_stations_cleaned.json', 'w', encoding='utf-8') as f:
    json.dump(cleaned_data, f, ensure_ascii=False, indent=4)

print("Dados limpos foram salvos em 'brt_stations_cleaned.json'")
