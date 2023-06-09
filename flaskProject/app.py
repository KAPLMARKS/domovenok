from flask import Flask
from pickle4 import pickle

app = Flask(__name__)


@app.route('/prediction')
def get_prediction():  # put application's code here
    with open('arima_model.pkl', 'rb') as f:
        loaded_model = pickle.load(f)

    # use the loaded model for prediction
    forecast = loaded_model.forecast(steps=1)
    print(forecast)
    a = str(forecast).split("    ")[1]
    b = str(a).split(" ")[0]
    c = str(a).split(".")[0]
    return c


if __name__ == '__main__':
    app.run()
