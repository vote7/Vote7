import React, { useEffect, useState, useContext } from "react";
import { RootContext } from "../../app/RootContext";
import Api from "../../api/Api"
import BarChart from 'react-bar-chart';

const PollResult = ({ pollId }) => {
    const [poll, setPoll] = useState([]);
    const [results, setResults] = useState([]);
    const {token} = useContext(RootContext);

    useEffect(() => {
        Api.getPoll(token, pollId).then(setPoll);
    }, []);
    useEffect(() => {
        Api.getResults(token, pollId).then(setResults);
    }, []);

    const prepared_results = results.map((result) => {
        var ans = result.result

        // Create items array
        var items = Object.keys(ans).map(function(key) {
            return [key, ans[key]];
        });

        // Sort the array based on the second element
        items.sort(function(first, second) {
            return second[1] - first[1];
        });

        // Create a new array with only the first 5 items
        ans = items.slice(0, 5).map(function(elem) {
            return {
                text: elem[0],
                value: elem[1]
            }
        })

        return {
            question: result.question.content,
            answers: ans
        }
    })

    const margin = {top: 20, right: 20, bottom: 30, left: 40};
    return (
        <>
            <div className="d-flex align-items-center mt-5 mb-3">
                <h1 className="m-0">Voting results - "{poll.name}"</h1>
            </div>
            {prepared_results.map((result) => {
                if(result.answers.length > 0)
                    return (
                        <>
                            <div style={{width: '50%'}}>
                                <h3>{result.question}</h3>
                                <BarChart width={500}
                                    height={200}
                                    margin={margin}
                                    data={result.answers}/>
                            </div>
                        </>
                    )
            })}
        </>
    );
};

export default PollResult;
