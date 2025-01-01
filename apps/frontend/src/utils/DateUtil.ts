export const formatDatetime = (date: string | number | Date): string => {
  const validDate = typeof date === "string" ? new Date(date) : date;

  return new Intl.DateTimeFormat("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
  }).format(new Date(validDate));
};
