
import pandas as pd
from pickle4 import pickle

df = data = pd.read_csv("viborka.csv")
data.head()

data['gender'] = data['gender'].replace({'female': 0, 'male': 1})

print(data)

from statsmodels.tsa.arima.model import ARIMA

student_count = data.groupby('year')['gender'].count()
print(student_count)

model = ARIMA(student_count, order=(2, 1, 2))
fit_model = model.fit()

# save the model
with open('arima_model.pkl', 'wb') as f:
    pickle.dump(fit_model, f)

forecast = fit_model.forecast(steps=1)

print(forecast)