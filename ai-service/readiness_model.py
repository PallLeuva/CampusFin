import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import Pipeline


# ---------------------------------------------------------
# Synthetic training data
# Each row contains:
# budgeting
# college cost knowledge
# scholarship knowledge
# credit knowledge
# debt knowledge
# emergency fund knowledge
# savings habit
# confidence
#
# Values range from 1 to 5
# ---------------------------------------------------------

X = np.array([
    [1, 1, 1, 1, 1, 1, 1, 1],
    [1, 2, 1, 1, 2, 1, 1, 2],
    [2, 2, 2, 2, 2, 2, 2, 2],
    [2, 3, 2, 2, 2, 2, 2, 3],
    [3, 3, 3, 2, 3, 3, 3, 3],
    [3, 3, 3, 3, 3, 3, 3, 3],
    [3, 4, 3, 3, 3, 3, 4, 3],
    [4, 3, 4, 3, 4, 4, 3, 4],
    [4, 4, 4, 3, 4, 4, 4, 4],
    [4, 4, 4, 4, 4, 4, 4, 4],
    [4, 5, 4, 4, 4, 4, 4, 5],
    [5, 4, 5, 4, 4, 5, 5, 4],
    [5, 5, 4, 4, 5, 5, 5, 5],
    [5, 5, 5, 5, 5, 5, 5, 5]
])

# 0 = Needs Improvement
# 1 = Developing Readiness
# 2 = Moderately Ready
# 3 = Highly Ready

y = np.array([
    0,
    0,
    0,
    1,
    1,
    1,
    2,
    2,
    2,
    2,
    3,
    3,
    3,
    3
])


# ---------------------------------------------------------
# Build model
# ---------------------------------------------------------

model = Pipeline([
    ("scaler", StandardScaler()),
    (
        "classifier",
        LogisticRegression(
            max_iter=1000,
            random_state=42
        )
    )
])

model.fit(X, y)


# ---------------------------------------------------------
# Convert prediction to readable label
# ---------------------------------------------------------

labels = {
    0: "Needs Improvement",
    1: "Developing Readiness",
    2: "Moderately Ready",
    3: "Highly Ready"
}


def predict_readiness(
    budgeting,
    college_cost,
    scholarship,
    credit,
    debt,
    emergency_fund,
    savings,
    confidence
):

    student = np.array([[
        budgeting,
        college_cost,
        scholarship,
        credit,
        debt,
        emergency_fund,
        savings,
        confidence
    ]])

    prediction = model.predict(student)[0]

    probabilities = model.predict_proba(student)[0]

    confidence_score = probabilities[prediction] * 100

    return {
        "prediction": labels[prediction],
        "confidence": round(confidence_score, 1)
    }


# ---------------------------------------------------------
# Test example
# ---------------------------------------------------------

if __name__ == "__main__":

    result = predict_readiness(
        budgeting=5,
        college_cost=4,
        scholarship=4,
        credit=3,
        debt=3,
        emergency_fund=4,
        savings=4,
        confidence=4
    )

    print("AI readiness prediction:", result["prediction"])
    print("Model confidence:", result["confidence"], "%")