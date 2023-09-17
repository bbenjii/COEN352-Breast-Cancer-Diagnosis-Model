
# Machine-Learning Model for Breast Cancer Diagnosis
(project from COEN352: Data Structure and Algorithm)


This code, written in Java, is a model that uses machine-learning algorithms, to predict whether a patient has
cancerous cells based on a set of information. The model will be given a set of different
patients to train with; each patient’s information consists of: an ID, a cancer diagnosis,
and a set of 10-attributes commonly used to determine whether a patient has cancer or not.
The training algorithm is a a 10-dimensional kd-tree, and the predicting algorithm uses k-nearest neighbor search.

The dataset, "WDBC.txt", used to train the model is from: "https://www.kaggle.com/datasets/uciml/breast-cancer-wisconsin-data?resource=download".
It's a set containing 569 different instances each containing information about a patient's cell,
that is or is not cancerous.

