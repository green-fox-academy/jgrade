[
    examName: 'Cohort 01 Test Exam',
    scoringItems: [
            [
                name: 'Scoring Item 1',
                score: Math.max(testResults['Alternative Solution 1']['score'], testResults['Alternative Solution 2']['score']),
                maxScore: 1.0,
                visibility: 'visible'
            ],
            [
                name: 'Scoring Item 2',
                score: testResults['Single Solution']['score'] * 2,
                maxScore: 2,
                visibility: testResults['Single Solution']['visibility']
            ]
    ]
]
