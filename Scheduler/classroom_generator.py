import csv
import json
import random

def generate_classroom_data(unique_ucionica):
    return {
        "classroom": unique_ucionica,
        "projector": False if random.random() < 0.5 else True,
        "no_spaces": random.randint(10, 50),
        "computers": random.randint(0, 50) if random.random() < 0.5 else 0
    }

def process_csv(input_csv_path, output_json_path):
    unique_ucionicas = set()

    with open(input_csv_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            unique_ucionicas.add(row["Učionica"])

    classroom_data_list = [generate_classroom_data(unique_ucionica) for unique_ucionica in unique_ucionicas]

    with open(output_json_path, 'w') as jsonfile:
        json.dump(classroom_data_list, jsonfile, indent=4)

if __name__ == "__main__":
    input_csv_path = "Schedule/src/main/resources/csv/schedule.csv"
    output_json_folder = "Schedule/src/main/resources/classrooms.json"

    process_csv(input_csv_path, output_json_folder)
