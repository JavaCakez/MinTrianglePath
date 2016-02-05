javac MinTrianglePath.java

echo ''
echo 'Worksheet test. Hoping for: Minimal path is: 7 + 6 + 3 + 2 = 18 '
echo ''
./tests/worksheetTest.sh
echo ''
echo ''
echo ''

echo ''
echo 'Test with no input. Hoping an error is thrown and program does not run since an empty triangle cannot have a minimum path'
echo ''
./tests/nullTest.sh
echo ''
echo ''
echo ''

echo ''
echo 'Test with just the apex. Hoping correct minimum path is found consisting of just that value'
echo ''
./tests/singleNodeTest.sh
echo ''
echo ''
echo ''

echo ''
echo 'Basic test'
echo ''
./tests/basicTest001.sh
echo ''
echo ''
echo ''

echo ''
echo 'Basic test'
echo ''
./tests/basicTest002.sh
echo ''
echo ''
echo ''

echo ''
echo 'Basic test'
echo ''
./tests/basicTest003.sh
echo ''
echo ''
echo ''

echo ''
echo 'Multiple correct paths test. Hoping one path will be correctly found'
echo ''
./tests/multipleCorrectPathsTest001.sh
echo ''
echo ''
echo ''

echo ''
echo 'Multiple correct paths test. Hoping one path will be correctly found'
echo ''
./tests/multipleCorrectPathsTest002.sh
echo ''
echo ''
echo ''

echo ''
echo 'Negative Integers test. Hoping a minimal path will still be correctly found'
echo ''
./tests/negativeIntegersTest.sh
echo ''
echo ''
echo ''

echo ''
echo 'Non-Integer test. Hoping an error will be thrown'
echo ''
./tests/nonIntegerTest001.sh
echo ''
echo ''
echo ''

echo ''
echo 'Non-Integer test. Hoping an error will be thrown'
echo ''
./tests/nonIntegerTest002.sh
echo ''
echo ''
echo ''

echo ''
echo 'Non-Integer test. Hoping an error will be thrown'
echo ''
./tests/nonIntegerTest003.sh
echo ''
echo ''
echo ''

echo ''
echo 'Non-Integer test. Hoping an error will be thrown'
echo ''
./tests/nonIntegerTest004.sh
echo ''
echo ''
echo ''

echo ''
echo 'Wrong length test. Hoping an error will be thrown'
echo ''
./tests/wrongLengthTest001.sh
echo ''
echo ''
echo ''

echo ''
echo 'Wrong length test. Hoping an error will be thrown'
echo ''
./tests/wrongLengthTest002.sh
echo ''
echo ''
echo ''

echo ''
echo 'Wrong length test. Hoping an error will be thrown'
echo ''
./tests/wrongLengthTest003.sh
echo ''
echo ''
echo ''

echo ''
echo 'Wrong length test. Hoping an error will be thrown'
echo ''
./tests/wrongLengthTest004.sh
echo ''
echo ''
echo ''

echo ''
echo '100-row triangle. Hoping for run time < 0.1 seconds'
echo ''
./tests/complexTest001.sh
echo ''
echo ''
echo ''


echo ''
echo '500-row triangle. Hoping for run time < 0.5 seconds'
echo ''
./tests/complexTest002.sh
echo ''
echo ''
echo ''