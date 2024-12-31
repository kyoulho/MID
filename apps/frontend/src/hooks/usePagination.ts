import { useCallback, useMemo, useState } from "react";

export interface PaginationResult<T> {
  currentPage: number;
  totalPages: number;
  paginatedData: T[];
  setPage: (page: number) => void;
}

export const usePagination = <T>(
  data: T[],
  itemsPerPage: number = 10,
): PaginationResult<T> => {
  const [currentPage, setCurrentPage] = useState(1);

  const totalPages = Math.max(1, Math.ceil(data.length / itemsPerPage));

  const paginatedData = useMemo(
    () =>
      data.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage),
    [data, currentPage, itemsPerPage],
  );

  const setPage = useCallback(
    (page: number) => setCurrentPage(Math.max(1, Math.min(page, totalPages))),
    [totalPages],
  );

  return { currentPage, totalPages, paginatedData, setPage };
};
