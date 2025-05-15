# Detection of LLM-Generated Java Code Using Discretized Nested Bigrams

[![Chat-GPT-Image-May-15-2025-01-35-47-PM.png](https://i.postimg.cc/6QZ8Xn7b/Chat-GPT-Image-May-15-2025-01-35-47-PM.png)](https://postimg.cc/Y4prN4zQ)

[![arXiv](https://img.shields.io/badge/arXiv-2502.15740-b31b1b.svg)](https://arxiv.org/abs/2502.15740)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Authors:** Timothy Paek, Chilukuri Mohan (Syracuse University)

This repository contains the code for the research paper "Detection of LLM-Generated Java Code Using Discretized Nested Bigrams." We address the authorship attribution problem for source code, focusing on distinguishing between human-written and LLM-generated Java code fragments. Our approach introduces novel **Discretized Nested Bigram Frequency (EWD-NB-F)** features, which significantly outperform existing methods by effectively representing sparse Abstract Syntax Tree (AST) information in dense membership bins.

## Abstract

Large Language Models (LLMs) are currently used extensively to generate code by professionals and students, motivating the development of tools to detect LLM-generated code for applications such as academic integrity and cybersecurity. We address this authorship attribution problem as a binary classification task along with feature identification and extraction. We propose new Discretized Nested Bigram Frequency features on source code groups of various sizes. Compared to prior work, improvements are obtained by representing sparse information in dense membership bins. Experimental evaluation demonstrated that our approach significantly outperformed a commonly used GPT code-detection API and baseline features, with accuracy exceeding 96% compared to 72% and 79% respectively in detecting GPT-rewritten Java code fragments for 976 files with GPT 3.5 and GPT 4 using 12 features. We also outperformed three prior works on code author identification in a 40-author dataset. Our approach scales well to larger data sets, and we achieved 99% accuracy and 0.999 AUC for 76,089 files and over 1,000 authors with GPT 4o using 227 features.

## Key Contributions

*   **Novel Stylometric Features:** Introduction of Discretized Nested Bigram Frequency (EWD-NB-F), Compressed Nested Bigram Frequency (CNB-F), and their combination with CodeBERT embeddings (EWD-CBNB-CM) for robust LLM-generated code detection.
*   **High Accuracy:** Achieved >96% accuracy on our GPT Dataset and 99% accuracy on our large-scale GPT GCJ Dataset, significantly outperforming baseline APIs like ZeroGPT.
*   **Scalability:** Demonstrated effectiveness on datasets with over 76,000 files and 1,000+ authors.
*   **Dataset Release:** Two new public datasets for LLM-generated Java code detection:
    *   [GPT-Java-Dataset](https://github.com/tipaek/GPT-Java-Dataset)
    *   [GPT-Java-GCJ-Dataset](https://github.com/tipaek/GPT-Java-GCJ-Dataset)

## System Architecture

Our approach involves two main stages:

1.  **Feature Extraction:**
    *   Java source code files are split into smaller "code groups" (typically 10-70 lines).
    *   **Dictionary Creation:** Abstract Syntax Trees (ASTs) are extracted. Syntactic features (Nested Bigrams, CodeBERT embeddings for AST nodes) are generated, and a dictionary mapping these features to unique indices is created.
    *   **Dataset Creation:** For each code group:
        *   Non-syntactic features (e.g., mean line length, whitespace) are extracted.
        *   Syntactic features are generated.
        *   Discretization (Equal Width Binning) is applied to syntactic features like NB-F and CBNB-CM to create dense, lower-dimensionality representations.
        *   Features are normalized.
    *   This process (illustrated in Figures 1 & 2 of the paper) results in a feature vector for each code group. The core logic for this is in `Code/FeatureExtractionCleaned.py`, orchestrated by `Testing/MakeDataset.py`.

    *Figure 1 from Paper: Feature Extraction Flowchart*
    ```
    [Dataset Directory] -> [Dictionary Creation] -> [Dataset Creation] -> [Normalization] -> [Output Dataset]
    ```

2.  **Classification:**
    *   Standard ensemble machine learning models (Random Forest, XGBoost, LGBM, CatBoost) are trained on the extracted features.
    *   The task is treated as a binary classification problem (human-authored vs. LLM-generated).
    *   The script `Testing/Testing.py` is used for training and evaluation.

## Datasets

Two primary datasets were created and used in this research. They are publicly available:

1.  **GPT Dataset:**
    *   Contains 976 Java files.
    *   Base of 666 files from 11 human authors.
    *   A subset of these files was rewritten by GPT-3.5 and GPT-4.
    *   Focuses on fine-grained detection with different LLMs.
    *   Available at: [https://github.com/tipaek/GPT-Java-Dataset](https://github.com/tipaek/GPT-Java-Dataset)

2.  **GPT GCJ Dataset:**
    *   Contains 76,089 Java files.
    *   Base of 58,524 human-authored files from the 2020 Google Code Jam (over 1,000 participants).
    *   17,565 of these files were rewritten by the GPT-4o API.
    *   Demonstrates scalability and performance on a large, diverse dataset.
    *   Available at: [https://github.com/tipaek/GPT-Java-GCJ-Dataset](https://github.com/tipaek/GPT-Java-GCJ-Dataset)

Additionally, a 40-author dataset from [Yang et al., 2017] was used for comparison in code author identification tasks.

## Installation

1.  **Prerequisites:**
    *   Python 3.8+
    *   Git

2.  **Clone the Repository:**
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

3.  **Set up a Virtual Environment (Recommended):**
    ```bash
    python -m venv venv
    source venv/bin/activate  # On Windows: venv\Scripts\activate
    ```

4.  **Install Dependencies:**
    A `requirements.txt` file can be generated from the imports in the scripts. Key dependencies include:
    ```
    numpy
    pandas
    scikit-learn
    javalang
    torch
    transformers
    lightgbm
    xgboost
    catboost
    scipy
    chardet
    pyclustering
    kmedoids
    kneed
    # For specific experimental scripts:
    # tensorflow # For ffNN experiments
    # pyswarms   # For PSO experiments
    # pyod       # For kNN anomaly detection experiments
    # comet_ml   # For experiment logging
    # openai     # For MakeGCJDataset.py
    ```
    Install them using:
    ```bash
    pip install numpy pandas scikit-learn javalang torch transformers lightgbm xgboost catboost scipy chardet pyclustering kmedoids kneed
    # Optional: pip install tensorflow pyswarms pyod comet_ml openai
    ```

5.  **API Keys (Optional):**
    *   If you intend to regenerate the GPT GCJ dataset using `Testing/MakeGCJDataset.py`, you will need an OpenAI API key. Set it as an environment variable or place it in `config.py` (create this file if it doesn't exist):
        ```python
        # config.py
        OPENAI_API_KEY = "your_openai_api_key"
        ```
    *   Comet.ml API key is used in some scripts for experiment tracking. If you wish to use it, configure it according to Comet.ml documentation and potentially add to `config.py`.

## Reproducing Results

The following steps outline how to reproduce the main results from the paper.

### Step 1: Prepare Raw Code Data

*   Download the **GPT Dataset** and **GPT GCJ Dataset** from their respective GitHub repositories (links above).
*   Organize the Java files into a directory structure that `Testing/MakeDataset.py` can process. Typically, this involves a main data directory with subdirectories for human-authored code and LLM-generated code (e.g., `Data/GPT-Rewrite/Human/` and `Data/GPT-Rewrite/Anomalous/`).
*   (Optional) To recreate the LLM-generated portion of the GPT GCJ Dataset, you can use `Testing/MakeGCJDataset.py`. This requires an OpenAI API key.

### Step 2: Feature Extraction

The primary script for feature extraction is `Testing/MakeDataset.py`, which utilizes functions from `Code/FeatureExtractionCleaned.py`.

1.  **Configure `Testing/MakeDataset.py`:**
    *   Modify the `files` variable to point to the root directory of your raw Java code (e.g., your local copy of GPT Dataset or GPT GCJ Dataset).
    *   The script iterates through different group sizes (`temp = [10, 20, 30, 40, 50, 60, 70]`) and bin widths for discretization (`temp = [3000, 4000, ...]`). Adjust these as needed.
    *   The core feature extraction function called is typically `extract_features_with_equalWidthBinning` or `NBKL3` (for Nested Bigrams with KL-divergence like features, though the paper primarily highlights EWD-NB-F). The exact function and its parameters will determine the feature set generated (e.g., EWD-NB-F, EWD-CBNB-CM).

2.  **Run the script:**
    ```bash
    python Testing/MakeDataset.py
    ```
    This will generate CSV files (e.g., `GCJ-GPT.EWD.NB.G30.3000.csv`) containing the extracted features. These CSVs will be saved in the same directory as the script or a specified output path.

### Step 3: Training and Evaluation

The primary script for training the ensemble models and evaluating them is `Testing/Testing.py`.

1.  **Configure `Testing/Testing.py`:**
    *   Modify the `paths` variable to point to the directory containing your generated feature CSV files (from Step 2).
    *   The script is set up to iterate through different group sizes (`group_sizes`) and load the corresponding CSVs. Ensure the naming convention matches.
    *   It uses `lightgbm`, `xgboost`, `catboost`, and `sklearn.ensemble.RandomForestClassifier`.
    *   Comet.ml is integrated for experiment logging. You can comment out these parts if not needed.

2.  **Run the script:**
    ```bash
    python Testing/Testing.py
    ```
    The script will output performance metrics (AUC, F1-score, Accuracy, Precision) for each ensemble model and configuration.

## Expected Performance

Our approach achieves state-of-the-art results:

*   **GPT Dataset (976 files, GPT-3.5/4):**
    *   Accuracy: >96% (EWD-NB-F with 12 features).
    *   AUC: ~0.974 (EWD-NB-F), ~0.979 (EWD-NB-F + EWD-CBNB-CM).
    *   Significantly outperforms ZeroGPT API (72-73% accuracy).
    *   *(Refer to Tables 1 & 2 in the paper for detailed feature comparisons)*

*   **40-Author Dataset (3,021 files, author identification):**
    *   Accuracy: Up to 99% (EWD-NB-F + EWD-CBNB-CM with 85 features).
    *   Outperforms three prior works.
    *   *(Refer to Tables 3 & 4 in the paper)*

*   **GPT GCJ Dataset (76,089 files, GPT-4o, 1k+ authors):**
    *   Accuracy: 99% (EWD-NB-F with ~227 features).
    *   AUC: 0.999.
    *   *(Refer to Table 5 in the paper)*

## Further Experiments (Optional)

The repository contains additional scripts for various experiments explored during the research:

*   **Feed-Forward Neural Networks:** Scripts in `Multi-author Tests/ffNN/` explore different ffNN architectures and stacking ensembles.
*   **Particle Swarm Optimization:** Scripts in `Multi-author Tests/PSO/` for feature selection and hyperparameter tuning.
*   **Clustering & Dimensionality Reduction:** Scripts in `Multi-author Tests/misc/` and `Testing/` (e.g., `PCADimensionalityReduction.py`, `TestDimensionalityReduction.py`) for experiments involving PCA, Autoencoders, and adding clustering features.
*   **API Comparison:** `Testing/TestAPIs.py` contains code for benchmarking against the ZeroGPT API.

To run these, inspect the individual scripts for their specific data input requirements and configurations.

## Citation

If you use this code or our datasets in your research, please cite our paper:

```bibtex
@article{PaekMohan2025LLMJava,
  title   = {Detection of LLM-Generated {Java} Code Using Discretized Nested Bigrams},
  author  = {Timothy Paek and Chilukuri Mohan},
  year    = {2025},
  journal = {arXiv preprint arXiv:2502.15740},
  eprint  = {2502.15740},
  archivePrefix = {arXiv},
  primaryClass = {cs.SE}
}
```

## Acknowledgements

The first author gratefully acknowledges support from ICCAE and the Renee Crown Honors program at Syracuse University and the Information Technology Services who provided a GPU in April 2024 for the final experiments whose results are reported.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details (assuming MIT, please create this file or choose another license).
