# =================  Description of relationship ===================
# The relationship I am modelling is the reltionship between the 
# number of hours spent studying and your mark on a test.
# ==================================================================

import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures

# Training set: (x = Hours spent studying || y = Scores on test)
x_train = [[0], [1], [2], [4], [6], [7], [8]] 
y_train = [[5], [15], [45], [75], [95], [100], [100]] 

# Testing set: (x = Hours spent studying || y = Scores on test)
x_test = [[2], [4], [8], [10]] 
y_test = [[35], [70], [100], [100]] 

# Train the Linear Regression model and plot a prediction
lr = LinearRegression()
lr.fit(x_train, y_train)
xx = np.linspace(0, 20, 100)
yy = lr.predict(xx.reshape(xx.shape[0], 1))

quadratic_featurizer = PolynomialFeatures(degree=2)

# This preprocessor transforms an input data matrix into a new data matrix of a given degree
X_train_quadratic = quadratic_featurizer.fit_transform(x_train)
X_test_quadratic = quadratic_featurizer.transform(x_test)

# Train and test the regressor_quadratic model
regressor_quadratic = LinearRegression()
regressor_quadratic.fit(X_train_quadratic, y_train)
xx_quadratic = quadratic_featurizer.transform(xx.reshape(xx.shape[0], 1))

# Plotting the linear regression line
plt.plot(x_train, lr.predict(x_train))

# Plotting polynomial regression
plt.plot(xx, regressor_quadratic.predict(xx_quadratic), c='r', linestyle='--')

# Plotting lables
plt.title('Score on test regressed on hours spent studying')
plt.xlabel('Hours spent studying')
plt.ylabel('Score on test')

# Plotting axis
plt.axis([0, 10, 0, 110])

# Plotting a grid
plt.grid(True)

# Scattering points
plt.scatter(x_train, y_train)

# Displaying the graph
plt.show()
