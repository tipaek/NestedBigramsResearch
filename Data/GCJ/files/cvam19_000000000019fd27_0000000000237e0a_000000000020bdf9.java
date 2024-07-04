from collections import OrderedDict
test_cases = int(input())


for i in range(test_cases):
    number_of_activities = int(input())
    activities = []
    cameronEndTime = -1
    jaimeEndTime = -1
    orderedSequence = OrderedDict()

    for j in range(number_of_activities):
        start, end = map(int, input().split())
        activity = (start, end)
        activities.append(activity)

        if str(activity) in orderedSequence.keys():
            secondaryStr = str(activity)+"_1"
            if secondaryStr in orderedSequence.keys():
                print("Case #{}: IMPOSSIBLE".format(i + 1))
                activities = []
                break
            else:
                orderedSequence[secondaryStr] = None

        else:
            orderedSequence[str(activity)] = None

    if len(activities) == 0:
        continue

    activities.sort()

    cameronEndTime = activities[0][1]
    orderedSequence[str(activities[0])] = 'C'

    for j in range(1, len(activities)):
        activity = activities[j]
        start = activity[0]
        end = activity[1]
        secondaryStr = str(activity)+"_1"

        if start >= cameronEndTime:
            cameronEndTime = end
            if orderedSequence[str(activity)] is not None:
                orderedSequence[secondaryStr] = 'C'
            else:
                orderedSequence[str(activity)] = 'C'

        elif start >= jaimeEndTime:
            jaimeEndTime = end
            if orderedSequence[str(activity)] is not None:
                orderedSequence[secondaryStr] = 'J'
            else:
                orderedSequence[str(activity)] = 'J'

        else:
            break
    schedule = ''
    for value in orderedSequence.values():
        if value is None:
            break
        schedule += value
    if len(schedule) < number_of_activities:
        print("Case #{}: IMPOSSIBLE".format(i+1))
    else:
        print("Case #{}: {}".format(i + 1, schedule))