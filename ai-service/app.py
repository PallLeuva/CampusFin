from flask import Flask, request, jsonify

from readiness_model import predict_readiness


app = Flask(__name__)


@app.route("/health", methods=["GET"])
def health():

    return jsonify({
        "status": "ok",
        "service": "CampusFin AI Service"
    })


@app.route("/predict", methods=["POST"])
def predict():

    data = request.get_json()

    if data is None:
        return jsonify({
            "error": "Request body must contain JSON."
        }), 400

    required_fields = [
        "budgeting",
        "collegeCost",
        "scholarship",
        "credit",
        "debt",
        "emergencyFund",
        "savings",
        "confidence"
    ]

    for field in required_fields:

        if field not in data:

            return jsonify({
                "error": f"Missing field: {field}"
            }), 400

    try:

        values = [
            int(data["budgeting"]),
            int(data["collegeCost"]),
            int(data["scholarship"]),
            int(data["credit"]),
            int(data["debt"]),
            int(data["emergencyFund"]),
            int(data["savings"]),
            int(data["confidence"])
        ]

        for value in values:

            if value < 1 or value > 5:

                return jsonify({
                    "error":
                        "All readiness values must be between 1 and 5."
                }), 400

        result = predict_readiness(
            budgeting=values[0],
            college_cost=values[1],
            scholarship=values[2],
            credit=values[3],
            debt=values[4],
            emergency_fund=values[5],
            savings=values[6],
            confidence=values[7]
        )

        return jsonify(result)

    except (ValueError, TypeError):

        return jsonify({
            "error": "Invalid readiness values."
        }), 400


if __name__ == "__main__":

    app.run(
        host="127.0.0.1",
        port=5000,
        debug=False
    )