gitconst probabilities =  [0.5, 0.35, 0.15];

read_from_file(matrix);

show_results();

function show_results() {
    console.log("Назва критерію | Оптимальнi рiшення");
    console.log("___________________________________")

    process.stdout.write("Ваальда        | ");
    show_solutions(vaald_criteria());

    process.stdout.write("Севіджа        | ");
    show_solutions(savage_criteria());

    process.stdout.write("Гурвiца        | ");
    show_solutions(gurwitz_criteria());

    process.stdout.write("Байєса-Лапласа | ");
    show_solutions(bayesa_laplasa_criteria());

    process.stdout.write("Модальний      | ");
    show_solutions(modal_criteria());
}

function show_solutions(array) {

    array.forEach((elem) => {
        process.stdout.write(1 + elem + " ");
    });

    console.log();
    console.log("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
}

function vaald_criteria() {
    var vaald_array = min_in_row();

    return max_index(vaald_array);

}

function savage_criteria() {
    var savage_array = max_in_row();

    var max_differences_array = max_difference(savage_array);   
    
    return min_index(max_differences_array);
}

function gurwitz_criteria() {
    const alpha = 0.5;

    var gurwitz_array = [];

    var max_array = max_in_row();
    var min_array = min_in_row();

    for (var i = 0; i < max_array.length; i++) {
        gurwitz_array.push(
            (alpha * max_array[i]) + ((1 - alpha) * min_array[i])
        );
    }

    return max_index(gurwitz_array);
}


function bayesa_laplasa_criteria() {
    var row_sums_with_probabilities = [];
    var sum = 0;

    matrix.forEach((row) => {
        for (var i = 0; i < row.length; i++) {
            sum += row[i] * probabilities[i];
        }
        row_sums_with_probabilities.push(sum);
        sum = 0;
    });

    return max_index(row_sums_with_probabilities);
}

function modal_criteria() {
    var most_likely_index = max_index(probabilities);
    var most_likely_alternatives = [];

    matrix.forEach((row) => {
        most_likely_alternatives
        .push(row[most_likely_index]);
    });

    return max_index(most_likely_alternatives);
}



function max_difference(array) {
    var min_array = [];
    var max_differences_array = [];
    var counter = 0;

    matrix.forEach((row) => {
        min_array.push(min(row));
    });

    min_array.forEach((elem) => {
        max_differences_array.push(array[counter] - elem);
        counter++;
    });

    return max_differences_array;
}

function max_index(array) {
    var max_ = array[0];
    var max_indices = [];

    for (var i = 1; i < array.length; i++) {
        if (array[i] > max_) {
            max_ = array[i];
        }
    }

    for (var i = 0; i < array.length; i++) {
        if (array[i] === max_) {
            max_indices.push(i);
        }
    }

    return max_indices;
}

function min_index(array) {
    var min_ = array[0];
    var min_indices = [];

    for (var i = 1; i < array.length; i++) {
        if (array[i] < min_) {
            min_ = array[i];
        }
    }

    for (var i = 0; i < array.length; i++) {
        if (array[i] === min_) {
            min_indices.push(i);
        }
    }

    return min_indices;
}

function max(array) {
    return Math.max(...array);
}

function min(array) {
    return Math.min(...array);
}

function min_in_row() {
    var min_array = [];

    matrix.forEach((arr) => {
         min_array.push(Math.min(...arr));
    });

    return min_array;
}

function max_in_row() {
    var max_array = [];

    matrix.forEach((arr) => {
        max_array.push(Math.max(...arr));
    });

    return max_array;
}


const budget = read_budget();
const incomes = read_incomes();
const losses = read_losses();
const probability = read_probabilities();
const BGE = [budget[0], budget[1], budget[0], budget[1]];
let OGO = [];
let result = [];

console.log(`\t\t\t ______________________________________`);
console.log(`\t\t\t|                                      |`);
console.log(`\t\t\t|Таблиці вхідних умов для вузлів 1-4 |`);
console.log(`\t\t\t|______________________________________|`);
console.log();
console.log();

console.log(`Вузол 1. Побудова великого заводу негайно.`);
console.log();
console.log(`            |` + `Великий попит` + `  Низький попит`);
console.log(`____________ ` + `_____________` + `  _____________`);
console.log(`Дохід       |      ` + incomes[0] + `           ` + losses[0]);
console.log(`Ймовірність |      ` + probability[0] + `          ` + probability[1]);
OGO[0] = count_ogo(1, probability[0], incomes[0], probability[1], losses[0], 5);
result[0] = OGO[0] - budget[0];
console.log(`__________________________________________`);
console.log();

console.log(`Вузол 2. Побудова малого заводу негайно.`);
console.log();
console.log(`            |` + `Великий попит` + `  Низький попит`);
console.log(`____________ ` + `_____________` + `  _____________`);
console.log(`Дохід       |      ` + incomes[1] + `           ` + losses[1]);
console.log(`Ймовірність |      ` + probability[2] + `          ` + probability[3]);
console.log();
OGO[1] = count_ogo(1, probability[2], incomes[1], probability[3], losses[1], 5);
result[1] = OGO[1] - budget[1];
console.log(`__________________________________________`);
console.log();

console.log(`Вузол 3. Побудова великого заводу через 1 рік. Ймовірність - ` + probability[4]);
console.log();
console.log(`            |` + `Великий попит` + `  Низький попит`);
console.log(`____________ ` + `_____________` + `  _____________`);
console.log(`Дохід      |      ` + incomes[0] + `           ` + losses[0]);
console.log(`Ймовірність|      ` + probability[6] + `          ` + probability[7]);
console.log();
OGO[2] = count_ogo(probability[4], probability[6], incomes[0], probability[7], losses[0], 4);
result[2] = OGO[2] - budget[0];
console.log(`__________________________________________`);
console.log();

console.log(`Вузол 4. Побудова малого заводу через 1 рік. Ймовірність - ` + probability[5]);
console.log();
console.log(`            |` + `Великий попит` + `  Низький попит`);
console.log(`____________ ` + `_____________` + `  _____________`);
console.log(`Дохід      |      ` + incomes[1] + `           ` + losses[1]);
console.log(`Ймовірність|      ` + probability[6] + `          ` + probability[7]);
console.log();
OGO[3] = count_ogo(probability[5], probability[6], incomes[1], probability[7], losses[1], 4);
result[3] = OGO[3] - budget[1];
console.log(`__________________________________________`);
console.log();

console.log(`\t\t\t ______________________________________`);
console.log(`\t\t\t|                                      |`);
console.log(`\t\t\t|    Таблиця очікуваних доходів     |`);
console.log(`\t\t\t|______________________________________|`);
console.log();
console.log();
console.log();
console.log(`  `, `Вузол`, ` ОГО`, `БГЕ`, `  Очікувані доходи`);
console.log(`  `, `_____`, `______`, `___`, `  ________________`);
let max = 0;
let min = result[0];
let index1 = 0;
let index2 = 0;
for (let i = 0; i < 4; i++)
{
    if (result[i] > max)
    {
        max = result[i];
        index1 = i + 1;
    }
    if (result[i] < min)
    {
        min = result[i];
        index2 = i + 1;
    }
    console.log(`    ${(i + 1)}      ${round(OGO[i], 1)}      ${BGE[i]}      ${round(result[i], 2)}`);
}

console.log();
console.log(`Найефективніше рішення - ${index1} з доходом ${max} тис.`);
console.log(`Найменш ефективне рішення - ${index2} зі збитками ${(min * -1)} тис.`);

function count_ogo(prob, income_prob, income, loss_prob, loss, years) {
    return prob * (income_prob * income + loss_prob * loss) * years;
}

function round(value, precision) {
    var multiplier = Math.pow(10, precision || 0);
    return Math.round(value * multiplier) / multiplier;
}


const Max = (array) => Math.max(...array);
const max_index = (array) => array.reduce((acc, cur, index) => (cur === Max(array) ? [...acc, index] : acc), []);

const has_absolute_win = (candidate) => candidate > ((sum(votes) / 2) + 1);

const grade = (num, size) => size - num - 1;

const candidates = [
    [1, 1, 3, 2, 2],
    [2, 3, 2, 3, 1],
    [3, 2, 1, 1, 3]
];

const votes = [24, 13, 26, 16, 15];

const candidates_num = 3;

show_results();

function show_results() {
    console.log("Назва правила | Переможець");
    console.log("___________________________________________________")

    // process.stdout.write("Відносна більшість                     | ");
    // show_solutions(relative_most(candidates, false));

    // process.stdout.write("Відносна більшість з вибуванням        | ");
    // show_solutions(relative_most(candidates, true));

    process.stdout.write("Борда                                  | ");
    show_solutions(borda(candidates));

    process.stdout.write("Кондорсе                               | ");
    show_solutions(condorse(candidates));

    // process.stdout.write("Копленда                               | ");
    // show_solutions(coplend(candidates));

    // process.stdout.write("Сімпсона                               | ");
    // show_solutions(simpson(candidates));

}

function show_solutions(array) {

    array.forEach((elem, i) => {
        if (i == 0)
            process.stdout.write(1 + elem + " ");

    });

    console.log();
    console.log("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
}

function relative_most(candids, is_absolute) {
    let uppers = candids[0];
    let upper_indices = [];
    let results = [];

    for (let i = 0; i < candidates_num; i++) {
        upper_indices[i] = new Array();
    }

    for (let i = 0; i < uppers.length; i++) {
        upper_indices[uppers[i] - 1].push(i);
    }

    
    for (let i = 0; i < candidates_num; i++) {
        results[i] = votes_sum(upper_indices[i]);
    }

    if (!is_absolute) {
        return max_index(results);
    }    else {
        return max_index(second_tour_winner(results));
    }
}

function second_tour_winner(first_tour_results) {
    let winner = max_index(first_tour_results);
    let second = max_index(first_tour_results);
    first_tour_results.sort((a, b) => b - a);
    let first_tour_winner = first_tour_results[0];
    if(has_absolute_win(first_tour_winner)) {
        return max_index(first_tour_results)[0];
    } else {
        return first_tour_winner > sum(votes) - first_tour_winner ?
            winner : second;
    }
}

function borda(candids) {
    let results = new Array(candids.length).fill(0);

    candids.forEach((row, x) => {
        row.forEach((elem, y) => {
            results[elem - 1] += grade(x, candids.length) * votes[y];
        });
    });

    return max_index(results);
}

function condorse(candids) {
    let results = comparative_votes(candids);

    return condorse_winner(results);
}

function coplend(candids) {
    let results = comparative_votes(candids);
    let most = (sum(votes)) / 2;

    results.forEach((row, i) => {
        row.forEach((elem, j) => {
            results[i][j] = elem > most ? 1 : -1;
        });
    });

    let sums = coplend_sums(results);

    return new Array(1).fill(2);
}

function simpson(candids) {
    let results = comparative_votes(candids);

    let grades = [];

    results.forEach((row) => {
        grades.push(min(row));
    });

    return new Array(1).fill(2);
}

function coplend_sums(results) {
    let sums = [];
    
    results.forEach((row) => {
        sums.push(sum(row));
    });

    return sums;
}

function comparative_votes(candids) {
    let results = new Array(5).fill(new Array(3).fill(0));

    let transposed_candidates = transposed(candids);

    transposed_candidates.forEach((row, i) => {
        row.forEach((_, j) => {
            for (let ind = 0; ind < votes.length; ind++) {
                let cand = transposed_candidates[i].indexOf((j + 1));
                results[i][j] += votes[i] * is_greater(cand, transposed_candidates[ind].indexOf((j + 1)));
            }
        });
    });

    return results;
}

function condorse_winner(results) {
    let winner_counter = 0;
    let winners = [];

    results.forEach((row, i) => {
        for (let j = 0; j < row.length; j++) {
            if (results[i][j] < results[j][i]) {
                break;
            } else {
                winner_counter++;
            }
        }
        if (winner_counter == 3) {
            winners.push(i);
        }
        winner_counter = 0;
    });

    return (winners);
}

function is_greater(cand, j) {
    return j > cand ? 1 : 0;
}

function transposed(matrix) {
    return matrix[0].map((_, i) => matrix.map(row => row[i]));
}

function min(array) {
    let min = 100;

    array.forEach((elem) => {
        if (elem > 0)
            min = elem >= min ? min : elem;
    });

    return min;
}

function sum(array) {
    let sum = 0;
    for( let i = 0; i < array.length; i++ ){
        sum += parseInt( array[i], 10 );
    }
    return sum;
}


function votes_sum(array) {
    return array.reduce((acc, el) => acc + votes[el], 0);
}

function transposed(matrix) {
    return matrix[0].map((_, i) => matrix.map(row => row[i]));
}

function sums_array(rows) { 
    let sums = [];
    rows.forEach(row => {
        sums.push(sum(row)); 
    });

    return sums;
}

function sums_r_array(rows) { 
    let sums = [];
    rows.forEach((row => {
        sums.push(sum(row.map((el, ind) => el * weights[ind]))) 
    }));

    return sums;
}

function show_results() {
    process.stdout.write("# | вага | f1   |  f2  |  f3   |  f4  |  f5  |  f6 \n");
    marks.forEach((row, ind) => {
        process.stdout.write((ind + 1) + " | " + weights[ind] + " | ");
        show_arr(row);
    });

    process.stdout.write("S | ");
    let sums = sums_array(transposed(marks));
    show_arr(sums);

    process.stdout.write("Sr | ");
    let sums_r = sums_r_array(transposed(marks));
    show_arr(sums_r);
    
    }

function show_arr(array) {

    array.forEach((elem) => {
        process.stdout.write(new Number(elem).toFixed(2) + " | ");
    });

    console.log();
    console.log("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
}
