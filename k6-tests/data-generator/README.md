Random numbers array generator
---
Generate CSV feed files for testing of sorting API endpoint.

### Usage

> NPM package manager is required to run this program.


- Install dependencies `npm i`
- Generate file with random data to feed gatling `LargeNumberSetSimulation` and `MediumNumberSetSimulation`.
    ```shell
    npm start <NUMBER_OF_EXAMPLES> <COUNT_OF_NUMBERS_IN_ARRAY> <RANGE_GENERATION_START> <RANGE_GENERATION_END>
    ```
### Examples

This tool is not perfect and is meant to do its job not to be a masterpiece. Do not expect good input params validation etc.

  ```shell
  npm run start 100 5000 1 2147483647
  ```
**Description**: *Generate 100 examples where array to sort has 5 000 elements and values are from range 1 - 2147483647*