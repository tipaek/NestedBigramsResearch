#include <iostream>
#include <vector>
#include <algorithm>

void getData(int &p, std::vector<int> &skills) {
    int n;
    std::cin >> n >> p;
    skills.reserve(n);
    for (int i = 0; i < n; i++) {
        int skill = 0;
        std::cin >> skill;
        skills.emplace_back(skill);
    }
}

int minTrainingTime(std::vector<int> &skills, int p) {
    std::sort(skills.begin(), skills.end());
    int low = 0, high = p - 1;

    int currentResult = 0;
    for (int i = 0; i < high; i++) {
        currentResult += skills[high] - skills[i];
    }

    int minResult = currentResult;
    int remainingPossibilities = skills.size() - p;
    for (int i = 0; i < remainingPossibilities; i++) {
        currentResult -= skills[++high] - skills[low++];
        currentResult += p * (skills[high] - skills[high - 1]);
        minResult = std::min(minResult, currentResult);
    }

    return minResult;
}

int main() {
    int t = 0;
    std::cin >> t;
    for (int testCase = 0; testCase < t; testCase++) {
        int p;
        std::vector<int> skills;
        getData(p, skills);

        std::cout << "Case #" << testCase + 1 << ": " << minTrainingTime(skills, p) << std::endl;
    }
}