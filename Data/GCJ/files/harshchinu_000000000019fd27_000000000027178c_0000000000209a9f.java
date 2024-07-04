class Solution {
public:
    void moveZeroes(vector<int>& nums) {
        vector<int> v1;
        int cnt=0;
        for(int i=0;i<nums.size();i++) {
            if(nums[i]!=0)
                v1.push_back(nums[i]);
            else
                cnt++;
        }
        for(int i=0;i<cnt;i++)
            v1.push_back(0);
        nums=v1;
    }
};