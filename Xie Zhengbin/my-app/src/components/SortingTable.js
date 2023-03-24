import React, { useEffect, useMemo, useState } from 'react';
import { useTable, useSortBy } from 'react-table';
import { COLUMNS } from './columns';
import './table.css';

export const SortingTable = ({ searchData }) => {
  const columns = useMemo(() => COLUMNS, []);
  const [data, setData] = useState([]);
  const [averageOfSecondColumn, setAverageOfSecondColumn] = useState(null);
  const [minOfThirdColumn, setMinOfThirdColumn] = useState(null);
  const [selectedRowIndex, setSelectedRowIndex] = useState(null);

  useEffect(() => {
    if (searchData) {
      setData(
        searchData.map(row => ({
          ...row,
          discount: -Math.round(100 * (1 - row.lowestRecordedPrice / row.currentPrice)) / 100 + '%'
        }))
      );
    } else {
      setData([]);
    }
  }, [searchData]);



  const tableInstance = useTable(
    {
      columns,
      data
    },
    useSortBy
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow
  } = tableInstance;

  useEffect(() => {
    if (rows && rows.length > 0) {
      const values = rows
        .map(row => parseFloat(row.cells[1]?.value))
        .filter(value => !isNaN(value));
      if (values.length > 0) {
        const sum = values.reduce((acc, value) => acc + value, 0);
        setAverageOfSecondColumn(Math.round((sum / values.length)*100)/100);
      }
    } else {
      setAverageOfSecondColumn(0);
    }
  }, [rows]);

  useEffect(() => {
    if (rows && rows.length > 0) {
      const values = rows
        .map(row => parseFloat(row.cells[2]?.value))
        .filter(value => !isNaN(value));
      setMinOfThirdColumn(Math.min(...values));
    } else {
      setMinOfThirdColumn(0);
    }
  }, [rows]);

  const toggleSelectedRowToTop = rowIndex => {
    if (selectedRowIndex === rowIndex) {
      setSelectedRowIndex(null);
    } else {
      const [selectedRow] = rows.splice(rowIndex, 1);
      setData([selectedRow.original, ...rows.map(row => row.original)]);
      setSelectedRowIndex(0);
    }
  };

  return (
      <table {...getTableProps()}>
        <thead>
          {headerGroups.map(headerGroup => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map(column => (
                <th {...column.getHeaderProps(column.getSortByToggleProps())}>
                  {column.render('Header')}
                  <span>{column.isSorted ? (column.isSortedDesc ? ' \u2193' : ' \u2191') : ''}</span>
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map((row, index) => {
            prepareRow(row);
            return (
              <tr
                {...row.getRowProps()}
                onClick={() => {
                  toggleSelectedRowToTop(index);
                }}
                style={{ background: selectedRowIndex === index ? '#e6f7ff' : 'inherit' }}
              >
                {row.cells.map(cell => {
                  return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>;
                })}
              </tr>
            );
          })}
        </tbody>
        <thead>
            <tr>
            <th>CurrentAverage</th>
            <th>HistoryLowest</th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <td>{averageOfSecondColumn}</td>
            <td>{minOfThirdColumn}</td>
            </tr>
        </tbody>
      </table>
  )
}

